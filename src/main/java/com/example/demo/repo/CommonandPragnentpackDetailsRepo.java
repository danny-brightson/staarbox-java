package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CommonAndPragnentPackDetails;

@Repository
public interface CommonandPragnentpackDetailsRepo extends JpaRepository<CommonAndPragnentPackDetails, Long> {

	@Query(value = """
								   SELECT
    -- =========================
    -- Egg / Seed
    -- =========================
    p.IsEggAdded AS isEggAdded,

    E1.Id AS eggOrSeedId,
    E1.FruitAndNuts AS eggOrSeedName,
    E1.Category AS eggOrSeedCategory,
    p.EggOrSeedWeight AS eggOrSeedWeight,
    E1.Protein AS eggOrSeedProtein,
    E1.Fiber AS eggOrSeedFiber,
    E1.Fat AS eggOrSeedFat,
    E1.CarboHydreate AS eggOrSeedCarbs,
    E1.Sugar AS eggOrSeedSugar,

    -- =========================
    -- Fruits
    -- =========================
    f1.Id AS fruit1Id,
    f1.FruitAndNuts AS fruit1Name,
    f1.Category AS fruit1Category,
    p.Fruit1Weight,
    f1.Protein AS fruit1Protein,
    f1.Fiber AS fruit1Fiber,
    f1.Fat AS fruit1Fat,
    f1.CarboHydreate AS fruit1Carbs,
    f1.Sugar AS fruit1Sugar,

    f2.Id AS fruit2Id,
    f2.FruitAndNuts AS fruit2Name,
    f2.Category AS fruit2Category,
    p.Fruit2Weight,
    f2.Protein AS fruit2Protein,
    f2.Fiber AS fruit2Fiber,
    f2.Fat AS fruit2Fat,
    f2.CarboHydreate AS fruit2Carbs,
    f2.Sugar AS fruit2Sugar,

    f3.Id AS fruit3Id,
    f3.FruitAndNuts AS fruit3Name,
    f3.Category AS fruit3Category,
    p.Fruit3Weight,
    f3.Protein AS fruit3Protein,
    f3.Fiber AS fruit3Fiber,
    f3.Fat AS fruit3Fat,
    f3.CarboHydreate AS fruit3Carbs,
    f3.Sugar AS fruit3Sugar,

    f4.Id AS fruit4Id,
    f4.FruitAndNuts AS fruit4Name,
    f4.Category AS fruit4Category,
    p.Fruit4Weight,
    f4.Protein AS fruit4Protein,
    f4.Fiber AS fruit4Fiber,
    f4.Fat AS fruit4Fat,
    f4.CarboHydreate AS fruit4Carbs,
    f4.Sugar AS fruit4Sugar,

    f5.Id AS fruit5Id,
    f5.FruitAndNuts AS fruit5Name,
    f5.Category AS fruit5Category,
    p.Fruit5Weight,
    f5.Protein AS fruit5Protein,
    f5.Fiber AS fruit5Fiber,
    f5.Fat AS fruit5Fat,
    f5.CarboHydreate AS fruit5Carbs,
    f5.Sugar AS fruit5Sugar,

    f6.Id AS fruit6Id,
    f6.FruitAndNuts AS fruit6Name,
    f6.Category AS fruit6Category,
    p.Fruit6Weight,
    f6.Protein AS fruit6Protein,
    f6.Fiber AS fruit6Fiber,
    f6.Fat AS fruit6Fat,
    f6.CarboHydreate AS fruit6Carbs,
    f6.Sugar AS fruit6Sugar,

    -- =========================
    -- Nuts
    -- =========================
    n1.Id AS nut1Id,
    n1.FruitAndNuts AS nut1Name,
    n1.Category AS nut1Category,
    p.Nut1Weight,
    n1.Protein AS nut1Protein,
    n1.Fiber AS nut1Fiber,
    n1.Fat AS nut1Fat,
    n1.CarboHydreate AS nut1Carbs,
    n1.Sugar AS nut1Sugar,

    n2.Id AS nut2Id,
    n2.FruitAndNuts AS nut2Name,
    n2.Category AS nut2Category,
    p.Nut2Weight,
    n2.Protein AS nut2Protein,
    n2.Fiber AS nut2Fiber,
    n2.Fat AS nut2Fat,
    n2.CarboHydreate AS nut2Carbs,
    n2.Sugar AS nut2Sugar,

    n3.Id AS nut3Id,
    n3.FruitAndNuts AS nut3Name,
    n3.Category AS nut3Category,
    p.Nut3Weight,
    n3.Protein AS nut3Protein,
    n3.Fiber AS nut3Fiber,
    n3.Fat AS nut3Fat,
    n3.CarboHydreate AS nut3Carbs,
    n3.Sugar AS nut3Sugar,

    n4.Id AS nut4Id,
    n4.FruitAndNuts AS nut4Name,
    n4.Category AS nut4Category,
    p.Nut4Weight,
    n4.Protein AS nut4Protein,
    n4.Fiber AS nut4Fiber,
    n4.Fat AS nut4Fat,
    n4.CarboHydreate AS nut4Carbs,
    n4.Sugar AS nut4Sugar,

    n5.Id AS nut5Id,
    n5.FruitAndNuts AS nut5Name,
    n5.Category AS nut5Category,
    p.Nut5Weight,
    n5.Protein AS nut5Protein,
    n5.Fiber AS nut5Fiber,
    n5.Fat AS nut5Fat,
    n5.CarboHydreate AS nut5Carbs,
    n5.Sugar AS nut5Sugar,

    -- =========================
    -- Optional Items
    -- =========================
    o1.Id AS optional1Id,
    o1.FruitAndNuts AS optional1Name,
    o1.Category AS optional1Category,
    p.Optional1Weight,
    p.Optional1IsFruit,
    o1.Protein AS optional1Protein,
    o1.Fiber AS optional1Fiber,
    o1.Fat AS optional1Fat,
    o1.CarboHydreate AS optional1Carbs,
    o1.Sugar AS optional1Sugar,

    o2.Id AS optional2Id,
    o2.FruitAndNuts AS optional2Name,
    o2.Category AS optional2Category,
    p.Optional2Weight,
    p.Optional2IsFruit,
    o2.Protein AS optional2Protein,
    o2.Fiber AS optional2Fiber,
    o2.Fat AS optional2Fat,
    o2.CarboHydreate AS optional2Carbs,
    o2.Sugar AS optional2Sugar,

    o3.Id AS optional3Id,
    o3.FruitAndNuts AS optional3Name,
    o3.Category AS optional3Category,
    p.Optional3Weight,
    p.Optional3IsFruit,
    o3.Protein AS optional3Protein,
    o3.Fiber AS optional3Fiber,
    o3.Fat AS optional3Fat,
    o3.CarboHydreate AS optional3Carbs,
    o3.Sugar AS optional3Sugar,

    o4.Id AS optional4Id,
    o4.FruitAndNuts AS optional4Name,
    o4.Category AS optional4Category,
    p.Optional4Weight,
    p.Optional4IsFruit,
    o4.Protein AS optional4Protein,
    o4.Fiber AS optional4Fiber,
    o4.Fat AS optional4Fat,
    o4.CarboHydreate AS optional4Carbs,
    o4.Sugar AS optional4Sugar,

    o5.Id AS optional5Id,
    o5.FruitAndNuts AS optional5Name,
    o5.Category AS optional5Category,
    p.Optional5Weight,
    p.Optional5IsFruit,
    o5.Protein AS optional5Protein,
    o5.Fiber AS optional5Fiber,
    o5.Fat AS optional5Fat,
    o5.CarboHydreate AS optional5Carbs,
    o5.Sugar AS optional5Sugar,

    o6.Id AS optional6Id,
    o6.FruitAndNuts AS optional6Name,
    o6.Category AS optional6Category,
    p.Optional6Weight,
    p.Optional6IsFruit,
    o6.Protein AS optional6Protein,
    o6.Fiber AS optional6Fiber,
    o6.Fat AS optional6Fat,
    o6.CarboHydreate AS optional6Carbs,
    o6.Sugar AS optional6Sugar,

    -- =========================
    -- Sandwich
    -- =========================
    s.Id AS sandwichId,
    s.FruitAndNuts AS sandwichName,
    s.Category AS sandwichCategory,
    s.Protein AS sandwichProtein,
    s.Fiber AS sandwichFiber,
    s.Fat AS sandwichFat,
    s.CarboHydreate AS sandwichCarbs,
    s.Sugar AS sandwichSugar

			FROM commonandPragnentpackDetails p
			LEFT JOIN lkpfruitandnuts E1 ON E1.Id = p.EggOrSeed
			LEFT JOIN lkpfruitandnuts f1 ON f1.Id = p.Fruit1Id
			LEFT JOIN lkpfruitandnuts f2 ON f2.Id = p.Fruit2Id
			LEFT JOIN lkpfruitandnuts f3 ON f3.Id = p.Fruit3Id
			LEFT JOIN lkpfruitandnuts f4 ON f4.Id = p.Fruit4Id
			LEFT JOIN lkpfruitandnuts f5 ON f5.Id = p.Fruit5Id
			LEFT JOIN lkpfruitandnuts f6 ON f6.Id = p.Fruit6Id
			LEFT JOIN lkpfruitandnuts n1 ON n1.Id = p.Nut1Id
			LEFT JOIN lkpfruitandnuts n2 ON n2.Id = p.Nut2Id
			LEFT JOIN lkpfruitandnuts n3 ON n3.Id = p.Nut3Id
			LEFT JOIN lkpfruitandnuts n4 ON n4.Id = p.Nut4Id
			LEFT JOIN lkpfruitandnuts n5 ON n5.Id = p.Nut5Id
			LEFT JOIN lkpfruitandnuts o1 ON o1.Id = p.Optional1Id
			LEFT JOIN lkpfruitandnuts o2 ON o2.Id = p.Optional2Id
			LEFT JOIN lkpfruitandnuts o3 ON o3.Id = p.Optional3Id
			LEFT JOIN lkpfruitandnuts o4 ON o4.Id = p.Optional4Id
			LEFT JOIN lkpfruitandnuts o5 ON o5.Id = p.Optional5Id
			LEFT JOIN lkpfruitandnuts o6 ON o6.Id = p.Optional6Id
			LEFT JOIN lkpfruitandnuts s  ON s.Id  = p.SandwichId

			WHERE p.PackageTypeId = :packageTypeId
			  AND p.WeekdayId = :weekdayId
								""", nativeQuery = true)
	List<Object[]> getPackageItemView(@Param("packageTypeId") int packageTypeId, @Param("weekdayId") int weekdayId);

	@Query(value = """
				    SELECT
				        p.IsEggAdded AS isEggAdded,
				        E1.FruitAndNuts AS eggOrSeed,
				        p.EggOrSeedWeight AS eggOrSeedWeight,
				        IF(pis_egg.Packed IS TRUE, TRUE, FALSE) AS eggPacked,
				        f1.FruitAndNuts AS fruit1Name, p.Fruit1Weight,
				        IF(pis_f1.Packed IS TRUE, TRUE, FALSE) AS fruit1Packed,
				        f2.FruitAndNuts AS fruit2Name, p.Fruit2Weight,
				        IF(pis_f2.Packed IS TRUE, TRUE, FALSE) AS fruit2Packed,
				        f3.FruitAndNuts AS fruit3Name, p.Fruit3Weight,
				        IF(pis_f3.Packed IS TRUE, TRUE, FALSE) AS fruit3Packed,
				        f4.FruitAndNuts AS fruit4Name, p.Fruit4Weight,
				        IF(pis_f4.Packed IS TRUE, TRUE, FALSE) AS fruit4Packed,
				        f5.FruitAndNuts AS fruit5Name, p.Fruit5Weight,
				        IF(pis_f5.Packed IS TRUE, TRUE, FALSE) AS fruit5Packed,
				        f6.FruitAndNuts AS fruit6Name, p.Fruit6Weight,
				        IF(pis_f6.Packed IS TRUE, TRUE, FALSE) AS fruit6Packed,
				        n1.FruitAndNuts AS nut1Name, p.Nut1Weight,
				        IF(pis_n1.Packed IS TRUE, TRUE, FALSE) AS nut1Packed,
				        n2.FruitAndNuts AS nut2Name, p.Nut2Weight,
				        IF(pis_n2.Packed IS TRUE, TRUE, FALSE) AS nut2Packed,
				        n3.FruitAndNuts AS nut3Name, p.Nut3Weight,
				        IF(pis_n3.Packed IS TRUE, TRUE, FALSE) AS nut3Packed,
				        n4.FruitAndNuts AS nut4Name, p.Nut4Weight,
				        IF(pis_n4.Packed IS TRUE, TRUE, FALSE) AS nut4Packed,
				        n5.FruitAndNuts AS nut5Name, p.Nut5Weight,
				        IF(pis_n5.Packed IS TRUE, TRUE, FALSE) AS nut5Packed,
			-- Sandwich 
					        s.FruitAndNuts AS sandwichName,
					        IF(pis_s.Packed IS TRUE, TRUE, FALSE) AS sandwichPacked

				    FROM commonandPragnentpackDetails p

				    LEFT JOIN lkpfruitandnuts E1 ON E1.Id = p.eggOrSeed
				    LEFT JOIN lkpfruitandnuts f1 ON f1.Id = p.Fruit1Id
				    LEFT JOIN lkpfruitandnuts f2 ON f2.Id = p.Fruit2Id
				    LEFT JOIN lkpfruitandnuts f3 ON f3.Id = p.Fruit3Id
				    LEFT JOIN lkpfruitandnuts f4 ON f4.Id = p.Fruit4Id
				    LEFT JOIN lkpfruitandnuts f5 ON f5.Id = p.Fruit5Id
				    LEFT JOIN lkpfruitandnuts f6 ON f6.Id = p.Fruit6Id
				    LEFT JOIN lkpfruitandnuts n1 ON n1.Id = p.Nut1Id
				    LEFT JOIN lkpfruitandnuts n2 ON n2.Id = p.Nut2Id
				    LEFT JOIN lkpfruitandnuts n3 ON n3.Id = p.Nut3Id
				    LEFT JOIN lkpfruitandnuts n4 ON n4.Id = p.Nut4Id
				    LEFT JOIN lkpfruitandnuts n5 ON n5.Id = p.Nut5Id
			--  Sandwich join 
					    LEFT JOIN lkpfruitandnuts s ON s.Id = p.SandwichId



				    LEFT JOIN packeritemstatus pis_egg ON pis_egg.CustomerId = :customerId AND pis_egg.ProductId = p.eggOrSeed
				    LEFT JOIN packeritemstatus pis_f1 ON pis_f1.CustomerId = :customerId AND pis_f1.ProductId = p.Fruit1Id
				    LEFT JOIN packeritemstatus pis_f2 ON pis_f2.CustomerId = :customerId AND pis_f2.ProductId = p.Fruit2Id
				    LEFT JOIN packeritemstatus pis_f3 ON pis_f3.CustomerId = :customerId AND pis_f3.ProductId = p.Fruit3Id
				    LEFT JOIN packeritemstatus pis_f4 ON pis_f4.CustomerId = :customerId AND pis_f4.ProductId = p.Fruit4Id
				    LEFT JOIN packeritemstatus pis_f5 ON pis_f5.CustomerId = :customerId AND pis_f5.ProductId = p.Fruit5Id
				    LEFT JOIN packeritemstatus pis_f6 ON pis_f6.CustomerId = :customerId AND pis_f6.ProductId = p.Fruit6Id
				    LEFT JOIN packeritemstatus pis_n1 ON pis_n1.CustomerId = :customerId AND pis_n1.ProductId = p.Nut1Id
				    LEFT JOIN packeritemstatus pis_n2 ON pis_n2.CustomerId = :customerId AND pis_n2.ProductId = p.Nut2Id
				    LEFT JOIN packeritemstatus pis_n3 ON pis_n3.CustomerId = :customerId AND pis_n3.ProductId = p.Nut3Id
				    LEFT JOIN packeritemstatus pis_n4 ON pis_n4.CustomerId = :customerId AND pis_n4.ProductId = p.Nut4Id
				    LEFT JOIN packeritemstatus pis_n5 ON pis_n5.CustomerId = :customerId AND pis_n5.ProductId = p.Nut5Id

			--  Sandwich packed status 
					    LEFT JOIN packeritemstatus pis_s ON pis_s.CustomerId = :customerId AND pis_s.ProductId = p.SandwichId


				    WHERE p.PackageTypeId = :packageTypeId
				      AND p.WeekdayId = :weekdayId
				""", nativeQuery = true)
	List<Object[]> getPackStatusForCustomer(@Param("customerId") Long customerId,
			@Param("packageTypeId") Integer packageTypeId, @Param("weekdayId") Integer weekdayId);

}
