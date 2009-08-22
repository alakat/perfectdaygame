/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.bitonfire.perfectday.battlefield.generator.sector;

import es.bitonfire.perfectday.battlefield.generator.sector.model.DistributionFunction;
import es.bitonfire.perfectday.battlefield.generator.sector.model.LineDistributionFunction;
import es.bitonfire.perfectday.battlefield.generator.sector.model.PriorityTerrain;
import es.bitonfire.perfectday.battlefield.generator.sector.model.RandomDistributionFuction;
import es.bitonfire.perfectday.battlefield.generator.sector.model.Sector;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.battelfield.generator.MapGenerator;
import org.perfectday.logicengine.model.battelfield.generator.Terrain;

/**
 * Generador de mapas basado en las siguientes hipotesis 1 El mapa subdividido
 * en sectore 2 Existe un modo de generar sectores 3 Existe un rango de
 * Obstaculos por sector [min,max] 4 Para cada mapa existe un conjunto de tipos
 * de Obstáculos con prioridades de aparición 5 Existe un modo de distribuir
 * obstáculos por el sector 6 Existe un conjunto de tipos de celdas priorizadas
 * 7 Existe un modo de "alisar" (convertir los sectores en un mapa correcto)
 * secotres
 * 
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class SectorMapGenerator extends MapGenerator {

	public static final int MAX_SECTOR_LEN = 5;
	public static final int MIN_SECTOR_LEN = 2;
	private Random random;
	private List<Sector> sectores;
	private Set<PriorityTerrain> terrains;
	private Set<PriorityTerrain> obstacules;
	private DistributionFunction typeDistributeFunction;
	private DistributionFunction obstaculeDistributionFunction;
	private Terrain terrainFiller;
	private Terrain terrainTypeMandatory;
	private Terrain terrainObstaculeMandatory;
	private Terrain connectionTerrain;
	private int xOffset;
	private int yOffset;

	public SectorMapGenerator(int xlen, int ylen,
			Set<PriorityTerrain> terrains, Set<PriorityTerrain> obstacules,
			DistributionFunction typeDistributeFunction,
			DistributionFunction obstaculeDistributionFunction,
			Terrain terrainFiller, Terrain terrainTypeMandatory,
			Terrain terrainObstaculeMandatory, Terrain connectionTerrain,
			int xOffset, int yOffset) {
		super(xlen, ylen);
		random = new Random(System.currentTimeMillis());
		sectores = new ArrayList<Sector>();
		this.terrains = terrains;
		this.obstacules = obstacules;
		this.typeDistributeFunction = typeDistributeFunction;
		this.obstaculeDistributionFunction = obstaculeDistributionFunction;
		this.terrainFiller = terrainFiller;
		this.terrainTypeMandatory = terrainTypeMandatory;
		this.terrainObstaculeMandatory = terrainObstaculeMandatory;
		this.connectionTerrain = connectionTerrain;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		generateSectors();
	}

	public SectorMapGenerator(int xlen, int ylen,
			Set<PriorityTerrain> terrains, Set<PriorityTerrain> obstacules) {
		super(xlen, ylen);
		this.terrains = terrains;
		this.obstacules = obstacules;
		random = new Random(System.currentTimeMillis());
		sectores = new ArrayList<Sector>();
	}

	public Field[][] getBattelfield() {
		Field[][] fields = new Field[this.xlen][this.ylen];
		for (Sector sector : this.getSectores()) {
			sector.distributeObstacules(this.obstacules,
					this.obstaculeDistributionFunction);
			sector.distributeTerrainType(this.terrains,
					this.typeDistributeFunction);
			this.obstaculeDistributionFunction.resert();
			this.typeDistributeFunction.resert();
		}
		this.paintAsciiMap();
		Terrain[][] terrains = this.fuzzyMap(terrainFiller,
				terrainObstaculeMandatory, terrainTypeMandatory,
				connectionTerrain, xOffset, yOffset);
		for(int i=0;i<terrains.length;i++){
            for(int j=0;j<terrains[0].length;j++){
                System.out.print("\t"+terrains[i][j].toTag());
            }
            System.out.println("");
        }
		//TODO pasar de terrain[][] a field[][]
		return fields;
	}

	/**
	 * Genera la lista de sectores
	 */
	public void generateSectors() {
		int px = 0; // Posición en la x;
		int py = 0; // posición en la y;
		while ((px < xlen)) {

			while ((py < ylen)) {
				int x = getNextSize();
				int y = getNextSize();
				this.sectores.add(new Sector(x, y));
				py += y;
			}
			px += MAX_SECTOR_LEN;
			py = 0;
		}
	}

	public Set<PriorityTerrain> getObstacules() {
		return obstacules;
	}

	public Set<PriorityTerrain> getTerrains() {
		return terrains;
	}

	public List<Sector> getSectores() {
		return sectores;
	}

	public void paintAsciiMap() {
		System.out.println("SectorMapGenerator.paintAsciiMap");
		String[][] smap = new String[10 + SectorMapGenerator.MAX_SECTOR_LEN][12 + SectorMapGenerator.MAX_SECTOR_LEN];
		for (int i = 0; i < smap.length; i++) {
			String[] strings = smap[i];
			for (int j = 0; j < strings.length; j++) {
				strings[j] = "_";

			}
		}
		int x = 0;
		int y = 0;
		for (Sector sector : this.sectores) {
			sector.paint(smap, x, y);

			x += SectorMapGenerator.MAX_SECTOR_LEN;
			y += SectorMapGenerator.MAX_SECTOR_LEN;
			if ((x >= this.xlen)) {
				x = 0;
			}
			if (y >= this.ylen) {
				y = 0;
			}
		}
		for (int i = 0; i < smap.length; i++) {
			for (int j = 0; j < smap[0].length; j++) {
				System.out.print(smap[i][j] + "\t");
			}
			System.out.println("");
		}

	}

	/**
	 * Devuelve true si la posición (i-1,j) o la posición (i,j-1) son del mismo
	 * tipo de terreno que conectionTerrain. Devuelve true también si llega a un
	 * borde
	 * 
	 * @param mapResult
	 * @param i
	 * @param j
	 * @param conectionTerrain
	 * @return
	 */
	private boolean backConnection(Terrain[][] mapResult, int i, int j,
			Terrain conectionTerrain) {
		try {
			if (mapResult[i - 1][j].and(Terrain.TYPE_MASK).isAnd(
					conectionTerrain)
					|| mapResult[i][j - 1].and(Terrain.TYPE_MASK).isAnd(
							conectionTerrain)) {
				return true;
			}
			return false;
		} catch (IndexOutOfBoundsException e) {
			/**
			 * Estan en un borde
			 */
			return true;
		}
	}

	/**
	 * Devuelve true si la posición (i+1,j) o la posición (i,j+1) son del mismo
	 * tipo de terreno que conectionTerrain. Devuelve true también si llega a un
	 * borde
	 * 
	 * @param mapResult
	 * @param i
	 * @param j
	 * @param conectionTerrain
	 * @return
	 */
	private boolean forwardConnection(Terrain[][] mapResult, int i, int j,
			Terrain conectionTerrain) {
		try {
			if (mapResult[i + 1][j].and(Terrain.TYPE_MASK).isAnd(
					conectionTerrain)
					|| mapResult[i][j + 1].and(Terrain.TYPE_MASK).isAnd(
							conectionTerrain)) {
				return true;
			}
			return false;
		} catch (IndexOutOfBoundsException e) {
			/**
			 * Estan en un borde
			 */
			return true;
		}
	}

	private int getNextSize() {
		int x = random.nextInt(MAX_SECTOR_LEN);
		while (x < MIN_SECTOR_LEN) {
			x = random.nextInt(MAX_SECTOR_LEN);
		}
		return x;
	}

	/**
	 * Alisa el conjunto de sectores a un mapa para dar la apariencia de un todo
	 * y no una composición de tiles.
	 * 
	 * @param terrainFiller
	 *            terreno con el que se completa las casillas sin ocupar por los
	 *            sectores Si es nulo no se tiene encuenta
	 * @param terrainObstaculeManadatory
	 *            obstaculo que debe ser precedido de un terreno especificado en
	 *            typeManadatory
	 * @param typeMandatory
	 *            terreno que precede al obtaculo obligatorio
	 * @param conectionTerrain
	 *            indica el tipo de terreno que debe ser conectado Si es nulo no
	 *            se tiene encuenta
	 * @param xOffset
	 *            número de celdas en el eje x que se ignora de los sectores y q
	 *            serán rellenada con terrainFiller
	 * @param yOffset
	 *            número de celdas en el eje y que se ignora de los sectores y q
	 *            serán rellenada con terrainFiller
	 */
	public Terrain[][] fuzzyMap(Terrain terrainFiller,
			Terrain terrainObstaculeManadatory, Terrain typeMandatory,
			Terrain conectionTerrain, int xOffset, int yOffset) {

		Terrain[][] mapResult = new Terrain[this.xlen + (2 * xOffset)][this.ylen
				+ (2 * yOffset)]; // mapa final
		System.out.println("Iniciamos el proceo ");
		this.paintAsciiMap();
		// Pintamos el terreno de relleno
		for (int i = 0; i < mapResult.length; i++) {
			Terrain[] terrains = mapResult[i];
			for (int j = 0; j < terrains.length; j++) {
				terrains[j] = terrainFiller;
			}
		}
		int xp = 0;
		int yp = 0;
		for (Sector sector : this.sectores) {
			sector.setTerrains(mapResult, (xp + xOffset), (yp + yOffset));
			xp += SectorMapGenerator.MAX_SECTOR_LEN;
			yp += SectorMapGenerator.MAX_SECTOR_LEN;
			if ((xp >= this.xlen)) {
				xp = 0;
			}
			if (yp >= this.ylen) {
				yp = 0;
			}
		}
		System.out.println("Tras copiar los sectores en el mapa");
		SectorMapGenerator.paintTerrainMap(mapResult);

		int maxLen = this.xlen > this.ylen ? this.xlen : this.ylen;
		if (terrainObstaculeManadatory != null) {
			/*
			 * Embellecemos algunos obtáculos colocando delante un typo de
			 * terreno determinado. Esto se usa en los matrones de asentamientos
			 */
			for (int k = 0; k < maxLen; k++) {
				for (int i = 0; (i < mapResult.length) && (i < k); i++) {
					for (int j = 0; (j < mapResult[0].length) && (j < k); j++) {
						Terrain terrain = mapResult[i][j];
						if (terrain.and(Terrain.OBSTACULE_MASK).isAnd(
								terrainObstaculeManadatory)) {
							if ((i + 1) < mapResult.length
									&& mapResult[i + 1][j].and(
											Terrain.OBSTACULE_MASK).isNull()) {
								mapResult[i + 1][j] = typeMandatory;
							} else if ((j + 1) < mapResult[0].length
									&& mapResult[i][j + 1].and(
											Terrain.OBSTACULE_MASK).isNull()) {
								mapResult[i][j + 1] = typeMandatory;
							}
						}
					}
				}
			}
		}
		// Pintamos la primera parte del proceso
		System.out.println("Propagamos el tipo que prece de un ");
		SectorMapGenerator.paintTerrainMap(mapResult);
		if (conectionTerrain != null) {
			/*
			 * Si queremos que algún tipo de terreno este conectado por ejemplo
			 * un camino
			 */
			Random r = new Random(System.currentTimeMillis());
			for (int k = 0; k < maxLen; k++) {
				for (int i = 0; (i < k) && (i < mapResult.length); i++) {
					for (int j = 0; (j < k) && (j < mapResult[0].length); j++) {
						if (mapResult[i][j].and(Terrain.TYPE_MASK).isAnd(
								conectionTerrain)) {
							/*
							 * Comprobamos si esta conectado hacia atras con
							 * otra del mismo tipo
							 */
							if (!backConnection(mapResult, i, j,
									conectionTerrain)) {
								int op = r.nextInt();
								if (op % 2 == 0 && ((i - 1) >= 0)) {
									mapResult[i - 1][j] = conectionTerrain;
								} else if ((j - 1) >= 0) {
									mapResult[i][j - 1] = conectionTerrain;
								}
							}

							/*
							 * Comprobamos si está conectada hacia delante con
							 * otra del mismo tipo
							 */
							if (!forwardConnection(mapResult, i, j,
									conectionTerrain)) {
								int op = r.nextInt();
								if (op % 2 == 0 && ((i + 1) < mapResult.length)) {
									mapResult[i + 1][j] = conectionTerrain;
								} else if ((j + 1) < mapResult[0].length) {
									mapResult[i][j + 1] = conectionTerrain;
								}
							}
						}
					}
				}
			}

		}
		System.out.println("..........fin............");
		return mapResult;
	}

	public static void paintTerrainMap(Terrain[][] terrains) {
		for (int i = 0; i < terrains.length; i++) {
			for (int j = 0; j < terrains[0].length; j++) {
				System.out.print("\t" + terrains[i][j].toTag());
			}
			System.out.println("");
		}
	}
}
