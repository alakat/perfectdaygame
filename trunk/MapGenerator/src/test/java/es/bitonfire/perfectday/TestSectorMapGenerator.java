/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitonfire.perfectday;

import es.bitonfire.perfectday.battlefield.generator.sector.SectorMapGenerator;
import es.bitonfire.perfectday.battlefield.generator.sector.model.DistributionFunction;
import es.bitonfire.perfectday.battlefield.generator.sector.model.LineDistributionFunction;
import es.bitonfire.perfectday.battlefield.generator.sector.model.PriorityTerrain;
import es.bitonfire.perfectday.battlefield.generator.sector.model.RandomDistributionFuction;
import es.bitonfire.perfectday.battlefield.generator.sector.model.Sector;
import java.util.HashSet;
import java.util.List;
import junit.framework.TestCase;
import org.perfectday.logicengine.model.battelfield.generator.Terrain;

/**
 * 
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class TestSectorMapGenerator extends TestCase {

	public void test_generateSector() {
		SectorMapGenerator map = new SectorMapGenerator(10, 12, null, null);

		String[][] smap = new String[10 + SectorMapGenerator.MAX_SECTOR_LEN][12 + SectorMapGenerator.MAX_SECTOR_LEN];
		for (int i = 0; i < smap.length; i++) {
			String[] strings = smap[i];
			for (int j = 0; j < strings.length; j++) {
				strings[j] = "_";

			}
		}
		map.generateSectors();
		List<Sector> sectors = map.getSectores();
		System.out.println("Sector Sizes" + sectors.size());
		int x = 0;
		int y = 0;
		for (Sector sector : sectors) {
			sector.paint(smap, x, y);
			x += SectorMapGenerator.MAX_SECTOR_LEN;
			y += SectorMapGenerator.MAX_SECTOR_LEN;
			if ((x >= 10)) {
				x = 0;
			}
			if (y >= 12) {
				y = 0;
			}
		}
		for (int i = 0; i < smap.length; i++) {
			String[] strings = smap[i];
			for (int j = 0; j < strings.length; j++) {
				System.out.print(strings[j] + "\t");

			}
			System.out.println("");
		}
	}

	public void test_randomDistributionFunction() {
		System.out.println("test_randomDistributionFunction");
		RandomDistributionFuction rdf = new RandomDistributionFuction(5, 5, 8);
		int[][] data = rdf.generateDistribution();
		for (int i = 0; i < data.length; i++) {
			int[] data_ = data[i];
			for (int j = 0; j < data_.length; j++) {
				int k = data_[j];
				System.out.print(k + "\t");
			}
			System.out.println("");
		}
	}

	public void test_linealDistributionFunction() {
		System.out.println("test_linealDistributionFunction");
		DistributionFunction rdf = new LineDistributionFunction(5, 5, 8);
		int[][] data = rdf.generateDistribution();
		for (int i = 0; i < data.length; i++) {
			int[] data_ = data[i];
			for (int j = 0; j < data_.length; j++) {
				int k = data_[j];
				System.out.print(k + "\t");
			}
			System.out.println("");
		}
	}

	public void test_generateSectorAndDistributeObstacules() {
		SectorMapGenerator map = new SectorMapGenerator(10, 12, null, null);
		HashSet<PriorityTerrain> obs = new HashSet<PriorityTerrain>();
		obs.add(new PriorityTerrain(Terrain.ROCK, 0.5));
		obs.add(new PriorityTerrain(Terrain.STONE_HOUSE, 0.2));
		obs.add(new PriorityTerrain(Terrain.WOOD_HOUSE, 0.3));

		String[][] smap = new String[10 + SectorMapGenerator.MAX_SECTOR_LEN][12 + SectorMapGenerator.MAX_SECTOR_LEN];
		for (int i = 0; i < smap.length; i++) {
			String[] strings = smap[i];
			for (int j = 0; j < strings.length; j++) {
				strings[j] = "_";

			}
		}
		map.generateSectors();
		List<Sector> sectors = map.getSectores();
		System.out.println("Sector Sizes" + sectors.size());
		int x = 0;
		int y = 0;
		for (Sector sector : sectors) {
			sector.distributeObstacules(obs, new RandomDistributionFuction(
					SectorMapGenerator.MAX_SECTOR_LEN,
					SectorMapGenerator.MAX_SECTOR_LEN, 7));
			sector.paint(smap, x, y);

			x += SectorMapGenerator.MAX_SECTOR_LEN;
			y += SectorMapGenerator.MAX_SECTOR_LEN;
			if ((x >= 10)) {
				x = 0;
			}
			if (y >= 12) {
				y = 0;
			}
		}
		for (int i = 0; i < smap.length; i++) {
			String[] strings = smap[i];
			for (int j = 0; j < strings.length; j++) {
				System.out.print(strings[j] + "\t");

			}
			System.out.println("");
		}
	}

	public void test_generateSectorAndDistributeObstaculesAndTerrainType() {
		System.out.println("All Sector data");
		SectorMapGenerator map = new SectorMapGenerator(10, 12, null, null);
		HashSet<PriorityTerrain> obs = new HashSet<PriorityTerrain>();
		obs.add(new PriorityTerrain(Terrain.ROCK, 0.5));
		obs.add(new PriorityTerrain(Terrain.STONE_HOUSE, 0.2));
		obs.add(new PriorityTerrain(Terrain.WOOD_HOUSE, 0.3));
		HashSet<PriorityTerrain> terr = new HashSet<PriorityTerrain>();
		terr.add(new PriorityTerrain(Terrain.PLAIN, 0.5));
		terr.add(new PriorityTerrain(Terrain.ROAD, 0.2));
		terr.add(new PriorityTerrain(Terrain.GRASS, 0.3));

		String[][] smap = new String[10 + SectorMapGenerator.MAX_SECTOR_LEN][12 + SectorMapGenerator.MAX_SECTOR_LEN];
		for (int i = 0; i < smap.length; i++) {
			String[] strings = smap[i];
			for (int j = 0; j < strings.length; j++) {
				strings[j] = "_";

			}
		}
		map.generateSectors();
		List<Sector> sectors = map.getSectores();
		System.out.println("Sector Sizes" + sectors.size());
		int x = 0;
		int y = 0;
		for (Sector sector : sectors) {
			sector.distributeObstacules(obs, new RandomDistributionFuction(
					SectorMapGenerator.MAX_SECTOR_LEN,
					SectorMapGenerator.MAX_SECTOR_LEN, 7));
			sector.distributeTerrainType(terr, new RandomDistributionFuction(
					SectorMapGenerator.MAX_SECTOR_LEN,
					SectorMapGenerator.MAX_SECTOR_LEN, 9));
			sector.paint(smap, x, y);

			x += SectorMapGenerator.MAX_SECTOR_LEN;
			y += SectorMapGenerator.MAX_SECTOR_LEN;
			if ((x >= 10)) {
				x = 0;
			}
			if (y >= 12) {
				y = 0;
			}
		}
		for (int i = 0; i < smap.length; i++) {
			String[] strings = smap[i];
			for (int j = 0; j < strings.length; j++) {
				System.out.print(strings[j] + "\t");

			}
			System.out.println("");
		}
	}

	/**
	 * Test de la generación completa de un mapa con patron de poblado
	 */
	public void test_generateMapaTownPatron() {
		SectorMapGenerator map = new SectorMapGenerator(10, 12, null, null);
		HashSet<PriorityTerrain> obs = new HashSet<PriorityTerrain>();
		obs.add(new PriorityTerrain(Terrain.STONE_HOUSE, 0.3));
		obs.add(new PriorityTerrain(Terrain.WOOD_HOUSE, 0.7));
		HashSet<PriorityTerrain> terr = new HashSet<PriorityTerrain>();
		terr.add(new PriorityTerrain(Terrain.PLAIN, 0.5));
		terr.add(new PriorityTerrain(Terrain.ROAD, 0.2));
		terr.add(new PriorityTerrain(Terrain.GRASS, 0.3));

		map.generateSectors();
		for (Sector sector : map.getSectores()) {
			sector.distributeObstacules(obs, new LineDistributionFunction(
					SectorMapGenerator.MAX_SECTOR_LEN,
					SectorMapGenerator.MAX_SECTOR_LEN, 3));
			sector.distributeTerrainType(terr, new RandomDistributionFuction(
					SectorMapGenerator.MAX_SECTOR_LEN,
					SectorMapGenerator.MAX_SECTOR_LEN, 9));
		}
		map.paintAsciiMap();
		Terrain[][] terrains = map.fuzzyMap(Terrain.GRASS, Terrain.STONE_HOUSE,
				Terrain.ROAD, Terrain.ROAD, 2, 2);
		for (int i = 0; i < terrains.length; i++) {
			for (int j = 0; j < terrains[0].length; j++) {
				System.out.print("\t" + terrains[i][j].toTag());
			}
			System.out.println("");
		}

	}

	public void test_generateMap() {
		HashSet<PriorityTerrain> obs = new HashSet<PriorityTerrain>();
		obs.add(new PriorityTerrain(Terrain.STONE_HOUSE, 0.3));
		obs.add(new PriorityTerrain(Terrain.WOOD_HOUSE, 0.7));
		HashSet<PriorityTerrain> terr = new HashSet<PriorityTerrain>();
		terr.add(new PriorityTerrain(Terrain.PLAIN, 0.5));
		terr.add(new PriorityTerrain(Terrain.ROAD, 0.2));
		terr.add(new PriorityTerrain(Terrain.GRASS, 0.3));
		SectorMapGenerator map = new SectorMapGenerator(10, 12, terr, obs,
				new RandomDistributionFuction(
						SectorMapGenerator.MAX_SECTOR_LEN,
						SectorMapGenerator.MAX_SECTOR_LEN, 9),
				new LineDistributionFunction(SectorMapGenerator.MAX_SECTOR_LEN,
						SectorMapGenerator.MAX_SECTOR_LEN, 3),Terrain.GRASS,Terrain.ROAD,Terrain.STONE_HOUSE,Terrain.ROAD, 2, 2);
		map.getBattelfield();
	}
}
