package com.example.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomizationSummaryDto;
import com.example.demo.dto.CustomizedPackageRequest;
import com.example.demo.dto.IngredientDto;
import com.example.demo.dto.OptionalIngredientDto;
import com.example.demo.dto.PackageResponseDto;
import com.example.demo.entity.CommonAndPragnentPackDetails;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.CustomizedPackageDetails;
import com.example.demo.entity.LkpFruitAndNuts;
import com.example.demo.repo.AvailableSandwichesRepository;
import com.example.demo.repo.CommonandPragnentpackDetailsRepo;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.CustomizedpackagedetailsRepo;
import com.example.demo.repo.LkpFruitAndNutsRepo;
import com.example.demo.repo.PackageTypeRepo;

@Service
public class CustomaizationService {

	private static final int FRUIT_JAR_PACK_DETAILS_ID = 15;
	private static final int DELIVERY_DAYS_PER_PLAN = 26;

	// @Autowired
	// private CustomerDetailsRepo customerRepo;

    @Autowired
    private com.example.demo.repo.AllergicPackageDetailsRepo AllergicPackageDetailsRepo;

    @Autowired
    private CommonandPragnentpackDetailsRepo commonandPragnentpackDetailsRepo;

    @Autowired
    private CustomizedpackagedetailsRepo customizedpackagedetailsRepo;

    @Autowired
    private PackageTypeRepo PackageTypeRepo;

    @Autowired
    private LkpFruitAndNutsRepo lkpFruitAndNutsRepo;

	@Autowired
	private AvailableSandwichesRepository availableSandwichesRepository;

    @Autowired
    private CustomerDetailsRepo customerDetailsRepo;

    public boolean isCommon(boolean isPregnant, boolean isAllergic, boolean isCustomized) {
        return !isPregnant && !isAllergic && !isCustomized;
    }

    public boolean doesCustomizedPackageExist(Long customerId, Long weekdayId, LocalDate date) {
        Long count = customizedpackagedetailsRepo.countCustomizedEntries(customerId, weekdayId, date);
        return count != null && count > 0;
    }

    public List<PackageResponseDto> getPackageByCustomerId(long customerId) {
        CustomerDetails customer = customerDetailsRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        LocalDate businessDate = LocalDate.now().plusDays(1);
        DayOfWeek day = businessDate.getDayOfWeek();
        int weekday = businessDate.getDayOfWeek().getValue();

        Boolean isCustomizedToday = doesCustomizedPackageExist(
                customerId,
                (long) weekday,
                businessDate
        );

        System.out.println("==== DEBUG START ====");
        System.out.println("CustomerId: " + customerId);
        System.out.println("BusinessDate: " + businessDate);
        System.out.println("Weekday: " + weekday);
        System.out.println("isCustomizedToday: " + isCustomizedToday);
        System.out.println("isCustomized flag (DB): " + customer.isCustomized());
        System.out.println("==== DEBUG END ====");

        boolean isCommonFlag = isCommon(
                customer.isPragnent(),
                customer.isAlergic(),
                Boolean.TRUE.equals(isCustomizedToday)
        );

        Integer packageTypeId = null;

        if (Boolean.FALSE.equals(isCustomizedToday)) {
            Integer optionalTypeId = PackageTypeRepo.getPackageTypeId(
                    customer.isPragnent(),
                    customer.isAlergic(),
                    isCustomizedToday,
                    isCommonFlag,
                    customer.getPackDetailsId()
            ).orElse(0);
            packageTypeId = optionalTypeId;
        }

        Boolean eggPreferdToSend = customer.isEggPreferd();

        if (packageTypeId != null && (packageTypeId == 12 || packageTypeId == 13)) {
            eggPreferdToSend = false;
        }

        List<Object[]> response = null;

        if (isCustomizedToday) {
            response = customizedpackagedetailsRepo.findDetailedPack(customerId, weekday, businessDate);
        } else if (Boolean.TRUE.equals(customer.isAlergic())) {
            response = AllergicPackageDetailsRepo.findDetailedPack(customerId, weekday);
        } else if (Boolean.TRUE.equals(customer.isPragnent())) {
            response = commonandPragnentpackDetailsRepo.getPackageItemView(packageTypeId, weekday);
        } else {
            response = commonandPragnentpackDetailsRepo.getPackageItemView(packageTypeId, weekday);
        }

        List<PackageResponseDto> responseList = new ArrayList<>();
        Map<String, Integer> indexMap = getIndexMap();
		List<LkpFruitAndNuts> activeJars = lkpFruitAndNutsRepo.findAllActiveJars();
		int packDetailsId = customer.getPackDetailsId();
		Map<Long, BigDecimal> jarDailyPrices = loadJarDailyPrices(packDetailsId, customer.getDistrictId());

        for (Object[] row : response) {

            PackageResponseDto dto = new PackageResponseDto();

            dto.setEggAdded(Boolean.TRUE.equals((Boolean) getValue(row, indexMap, "isEggAdded")));

            IngredientDto eggOrSeed = null;
            Object eggNameObj = getValue(row, indexMap, "eggOrSeedName");

            if (eggNameObj != null) {
                eggOrSeed = new IngredientDto();
                eggOrSeed.setId(getLong(row, indexMap, "eggOrSeedId"));
                eggOrSeed.setName(eggNameObj.toString());
                eggOrSeed.setCategory(getValue(row, indexMap, "eggOrSeedCategory").toString());
                Object w = getValue(row, indexMap, "eggOrSeedWeight");
                eggOrSeed.setWeight(w != null ? w.toString() : null);
                eggOrSeed.setProtein(getBD(row, indexMap, "eggOrSeedProtein"));
                eggOrSeed.setFiber(getBD(row, indexMap, "eggOrSeedFiber"));
                eggOrSeed.setFat(getBD(row, indexMap, "eggOrSeedFat"));
                eggOrSeed.setCarboHydreate(getBD(row, indexMap, "eggOrSeedCarboHydreate"));
                eggOrSeed.setSugar(getBD(row, indexMap, "eggOrSeedSugar"));
            }
            dto.setEggOrSeed(eggOrSeed);

            List<IngredientDto> fruits = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                Object nameObj = getValue(row, indexMap, "fruit" + i + "Name");
                if (nameObj == null) continue;

                IngredientDto fruit = new IngredientDto();
                fruit.setId(getLong(row, indexMap, "fruit" + i + "Id"));
                fruit.setName(nameObj.toString());
                fruit.setCategory(getValue(row, indexMap, "fruit" + i + "Category").toString());
                Object w = getValue(row, indexMap, "fruit" + i + "Weight");
                fruit.setWeight(w != null ? w.toString() : null);
                fruit.setProtein(getBD(row, indexMap, "fruit" + i + "Protein"));
                fruit.setFiber(getBD(row, indexMap, "fruit" + i + "Fiber"));
                fruit.setFat(getBD(row, indexMap, "fruit" + i + "Fat"));
                fruit.setCarboHydreate(getBD(row, indexMap, "fruit" + i + "CarboHydreate"));
                fruit.setSugar(getBD(row, indexMap, "fruit" + i + "Sugar"));
                fruits.add(fruit);
            }
            dto.setFruits(fruits);

            List<IngredientDto> nuts = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Object nameObj = getValue(row, indexMap, "nut" + i + "Name");
                if (nameObj == null) continue;

                IngredientDto nut = new IngredientDto();
                nut.setId(getLong(row, indexMap, "nut" + i + "Id"));
                nut.setName(nameObj.toString());
                nut.setCategory(getValue(row, indexMap, "nut" + i + "Category").toString());
                Object w = getValue(row, indexMap, "nut" + i + "Weight");
                nut.setWeight(w != null ? w.toString() : null);
                nut.setProtein(getBD(row, indexMap, "nut" + i + "Protein"));
                nut.setFiber(getBD(row, indexMap, "nut" + i + "Fiber"));
                nut.setFat(getBD(row, indexMap, "nut" + i + "Fat"));
                nut.setCarboHydreate(getBD(row, indexMap, "nut" + i + "CarboHydreate"));
                nut.setSugar(getBD(row, indexMap, "nut" + i + "Sugar"));
                nuts.add(nut);
            }
            dto.setNuts(nuts);

            List<OptionalIngredientDto> optionals = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                Object nameObj = getValue(row, indexMap, "optional" + i + "Name");
                if (nameObj == null) continue;

                OptionalIngredientDto opt = new OptionalIngredientDto();
                opt.setId(getLong(row, indexMap, "optional" + i + "Id"));
                opt.setName(nameObj.toString());
                opt.setCategory(getValue(row, indexMap, "optional" + i + "Category").toString());
                Object w = getValue(row, indexMap, "optional" + i + "Weight");
                opt.setWeight(w != null ? w.toString() : null);
                opt.setIsFruit(Boolean.TRUE.equals((Boolean) getValue(row, indexMap, "Optional" + i + "IsFruit")));
                opt.setFiber(getBD(row, indexMap, "optional" + i + "Fiber"));
                opt.setProtein(getBD(row, indexMap, "optional" + i + "Protein"));
                opt.setFat(getBD(row, indexMap, "optional" + i + "Fat"));
                opt.setCarboHydreate(getBD(row, indexMap, "optional" + i + "CarboHydreate"));
                opt.setSugar(getBD(row, indexMap, "optional" + i + "Sugar"));
                optionals.add(opt);
            }
            dto.setOptionals(optionals);

            IngredientDto sandwich = null;
            Object sandwichNameObj = getValue(row, indexMap, "sandwichName");

            if (sandwichNameObj != null) {
                sandwich = new IngredientDto();
                sandwich.setId(getLong(row, indexMap, "sandwichId"));
                sandwich.setName(sandwichNameObj.toString());
                sandwich.setCategory(getValue(row, indexMap, "sandwichCategory").toString());
                sandwich.setWeight(null);
                sandwich.setProtein(getBD(row, indexMap, "sandwichProtein"));
                sandwich.setFiber(getBD(row, indexMap, "sandwichFiber"));
                sandwich.setFat(getBD(row, indexMap, "sandwichFat"));
                sandwich.setCarboHydreate(getBD(row, indexMap, "sandwichCarboHydreate"));
                sandwich.setSugar(getBD(row, indexMap, "sandwichSugar"));
            }
            dto.setSandwich(sandwich);
			
			//Jar
			
			IngredientDto jar = null;

			Object jarNameObj = getValue(row, indexMap, "jarName");

			if (jarNameObj != null) {

			    jar = new IngredientDto();

			    jar.setId(getLong(row, indexMap, "jarId"));

			    jar.setName(jarNameObj.toString());

			    Object q = getValue(row, indexMap, "jarQuantity");

			    jar.setWeight(q != null ? q.toString() : null);
			}

			dto.setJar(jar);
			applyJarPresentationRules(dto, packDetailsId, activeJars, jarDailyPrices);
			normalizeResponseCategories(dto);

            responseList.add(dto);
        }

        return responseList;

	}

	private void applyJarPresentationRules(
			PackageResponseDto dto,
			int packDetailsId,
			List<LkpFruitAndNuts> activeJars,
			Map<Long, BigDecimal> jarDailyPrices) {
		if (packDetailsId == FRUIT_JAR_PACK_DETAILS_ID) {
			IngredientDto selectedJar = dto.getJar();
			dto.setOptionals(removeJarOptionals(dto.getOptionals()));
			enrichJarWithMacros(selectedJar, activeJars, jarDailyPrices);
			dto.setOptionals(appendJarOptionals(
					dto.getOptionals(),
					activeJars,
					jarDailyPrices,
					selectedJar,
					false));
			return;
		}

		IngredientDto selectedJar = dto.getJar();
		dto.setNuts(removeJarIngredients(dto.getNuts()));
		dto.setOptionals(removeJarOptionals(dto.getOptionals()));

		if (selectedJar != null && selectedJar.getId() != null) {
			enrichJarWithMacros(selectedJar, activeJars, jarDailyPrices);
			dto.setJar(selectedJar);
		} else {
			dto.setJar(null);
		}
	}

	private Map<Long, BigDecimal> loadJarDailyPrices(int packDetailsId, int districtId) {
		if (packDetailsId == FRUIT_JAR_PACK_DETAILS_ID) {
			BigDecimal fruitJarDailyPrice = resolveFruitJarDailyPrice(packDetailsId, districtId);
			Map<Long, BigDecimal> prices = new HashMap<>();
			for (LkpFruitAndNuts jar : lkpFruitAndNutsRepo.findAllActiveJars()) {
				if (jar.getId() != null) {
					prices.put(jar.getId().longValue(), fruitJarDailyPrice);
				}
			}
			return prices;
		}

		Map<Long, BigDecimal> prices = new HashMap<>();
		for (Object[] row : availableSandwichesRepository.findJarRatesByDistrict(districtId)) {
			if (row[0] == null || row[1] == null) {
				continue;
			}
			Long jarId = row[0] instanceof Number number ? number.longValue() : Long.valueOf(row[0].toString());
			BigDecimal rate = row[1] instanceof BigDecimal decimal
					? decimal
					: new BigDecimal(row[1].toString());
			prices.put(jarId, rate);
		}
		return prices;
	}

	private BigDecimal resolveFruitJarDailyPrice(int packDetailsId, int districtId) {
		Long packageRate = customerDetailsRepo.getPackageRate((long) packDetailsId, (long) districtId);
		if (packageRate == null || packageRate <= 0) {
			return BigDecimal.ZERO;
		}
		return BigDecimal.valueOf(packageRate)
				.divide(BigDecimal.valueOf(DELIVERY_DAYS_PER_PLAN), 2, RoundingMode.HALF_UP);
	}

	private List<OptionalIngredientDto> appendJarOptionals(
			List<OptionalIngredientDto> optionals,
			List<LkpFruitAndNuts> activeJars,
			Map<Long, BigDecimal> jarDailyPrices,
			IngredientDto selectedJar,
			boolean excludeSelectedJar) {
		List<OptionalIngredientDto> result = new ArrayList<>(optionals);
		Set<Long> existingIds = result.stream()
				.map(OptionalIngredientDto::getId)
				.filter(id -> id != null)
				.collect(Collectors.toCollection(HashSet::new));

		Long selectedJarId = selectedJar != null ? selectedJar.getId() : null;

		for (LkpFruitAndNuts jar : activeJars) {
			Long jarId = jar.getId() != null ? jar.getId().longValue() : null;
			if (jarId == null || existingIds.contains(jarId)) {
				continue;
			}
			if (excludeSelectedJar && selectedJarId != null && jarId.equals(selectedJarId)) {
				continue;
			}

			OptionalIngredientDto opt = toJarOptional(jar, jarDailyPrices);
			if (!excludeSelectedJar
					&& selectedJarId != null
					&& jarId.equals(selectedJarId)
					&& selectedJar.getWeight() != null
					&& !selectedJar.getWeight().isBlank()) {
				opt.setWeight(selectedJar.getWeight());
			}
			result.add(opt);
			existingIds.add(jarId);
		}
		return result;
	}

	private List<OptionalIngredientDto> removeJarOptionals(List<OptionalIngredientDto> optionals) {
		return optionals.stream()
				.filter(opt -> !isJarCategory(opt.getCategory()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private List<IngredientDto> removeJarIngredients(List<IngredientDto> items) {
		return items.stream()
				.filter(item -> !isJarCategory(item.getCategory()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private OptionalIngredientDto toJarOptional(LkpFruitAndNuts jar, Map<Long, BigDecimal> jarDailyPrices) {
		OptionalIngredientDto opt = new OptionalIngredientDto();
		Long jarId = jar.getId() != null ? jar.getId().longValue() : null;
		opt.setId(jarId);
		opt.setName(formatJarDisplayName(jar.getFruitAndNuts()));
		opt.setCategory(jar.getCategory());
		opt.setWeight(null);
		opt.setIsFruit(false);
		opt.setProtein(jar.getProtein());
		opt.setFiber(jar.getFiber());
		opt.setFat(jar.getFat());
		opt.setCarboHydreate(jar.getCarboHydreate());
		opt.setSugar(jar.getSugar());
		if (jarId != null) {
			opt.setDailyPrice(jarDailyPrices.get(jarId));
		}
		return opt;
	}

	private String formatJarDisplayName(String name) {
		if (name == null) {
			return null;
		}
		if ("MilkJar".equalsIgnoreCase(name)) {
			return "Milk Jar";
		}
		return name;
	}

	private void enrichJarWithMacros(
			IngredientDto jar,
			List<LkpFruitAndNuts> activeJars,
			Map<Long, BigDecimal> jarDailyPrices) {
		if (jar == null || jar.getId() == null) {
			return;
		}
		activeJars.stream()
				.filter(j -> j.getId() != null && jar.getId().equals(j.getId().longValue()))
				.findFirst()
				.ifPresent(j -> {
					jar.setName(formatJarDisplayName(j.getFruitAndNuts()));
					jar.setCategory(j.getCategory());
					jar.setProtein(j.getProtein());
					jar.setFiber(j.getFiber());
					jar.setFat(j.getFat());
					jar.setCarboHydreate(j.getCarboHydreate());
					jar.setSugar(j.getSugar());
					jar.setDailyPrice(jarDailyPrices.get(jar.getId()));
				});
	}

	private boolean isJarCategory(String category) {
		return category != null && category.equalsIgnoreCase("jar");
	}

	private void normalizeResponseCategories(PackageResponseDto dto) {
		normalizeIngredientCategory(dto.getEggOrSeed());
		if (dto.getFruits() != null) {
			dto.getFruits().forEach(this::normalizeIngredientCategory);
		}
		if (dto.getNuts() != null) {
			dto.getNuts().forEach(this::normalizeIngredientCategory);
		}
		if (dto.getOptionals() != null) {
			dto.getOptionals().forEach(this::normalizeOptionalCategory);
		}
		normalizeIngredientCategory(dto.getSandwich());
		normalizeIngredientCategory(dto.getJar());
	}

	private void normalizeIngredientCategory(IngredientDto ingredient) {
		if (ingredient != null) {
			ingredient.setCategory(normalizeCategory(ingredient.getCategory()));
		}
	}

	private void normalizeOptionalCategory(OptionalIngredientDto optional) {
		if (optional != null) {
			optional.setCategory(normalizeCategory(optional.getCategory()));
		}
	}

	private String normalizeCategory(String category) {
		if (category == null) {
			return null;
		}
		String compact = category.trim().toLowerCase().replace(" ", "");
		if ("nuts&seeds".equals(compact)) {
			return "nuts&seeds";
		}
		return category.trim();
    }

    private Long getLong(Object[] row, Map<String, Integer> indexMap, String key) {
        Object val = getValue(row, indexMap, key);
        if (val == null) return null;
        if (val instanceof Integer) return ((Integer) val).longValue();
        if (val instanceof Long) return (Long) val;
        return Long.valueOf(val.toString());
    }

    private Object getValue(Object[] row, Map<String, Integer> map, String key) {
        Integer idx = map.get(key);
        if (idx == null || idx < 0 || idx >= row.length) return null;
        return row[idx];
    }

    private BigDecimal getBD(Object[] row, Map<String, Integer> map, String key) {
        return toBigDecimal(getValue(row, map, key));
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) return BigDecimal.ZERO;
        if (value instanceof BigDecimal) return (BigDecimal) value;
        if (value instanceof Number) return BigDecimal.valueOf(((Number) value).doubleValue());
        try {
            return new BigDecimal(value.toString().trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number: " + value);
        }
    }

    private Map<String, Integer> getIndexMap() {
        Map<String, Integer> map = new HashMap<>();
        int i = 0;

        map.put("isEggAdded", i++);
        map.put("eggOrSeedId", i++);
        map.put("eggOrSeedName", i++);
        map.put("eggOrSeedCategory", i++);
        map.put("eggOrSeedWeight", i++);
        map.put("eggOrSeedProtein", i++);
        map.put("eggOrSeedFiber", i++);
        map.put("eggOrSeedFat", i++);
        map.put("eggOrSeedCarboHydreate", i++);
        map.put("eggOrSeedSugar", i++);

        for (int j = 1; j <= 6; j++) {
            map.put("fruit" + j + "Id", i++);
            map.put("fruit" + j + "Name", i++);
            map.put("fruit" + j + "Category", i++);
            map.put("fruit" + j + "Weight", i++);
            map.put("fruit" + j + "Protein", i++);
            map.put("fruit" + j + "Fiber", i++);
            map.put("fruit" + j + "Fat", i++);
            map.put("fruit" + j + "CarboHydreate", i++);
            map.put("fruit" + j + "Sugar", i++);
        }

        for (int j = 1; j <= 5; j++) {
            map.put("nut" + j + "Id", i++);
            map.put("nut" + j + "Name", i++);
            map.put("nut" + j + "Category", i++);
            map.put("nut" + j + "Weight", i++);
            map.put("nut" + j + "Protein", i++);
            map.put("nut" + j + "Fiber", i++);
            map.put("nut" + j + "Fat", i++);
            map.put("nut" + j + "CarboHydreate", i++);
            map.put("nut" + j + "Sugar", i++);
        }

        for (int j = 1; j <= 6; j++) {
            map.put("optional" + j + "Id", i++);
            map.put("optional" + j + "Name", i++);
            map.put("optional" + j + "Category", i++);
            map.put("optional" + j + "Weight", i++);
            map.put("Optional" + j + "IsFruit", i++);
            map.put("optional" + j + "Protein", i++);
            map.put("optional" + j + "Fiber", i++);
            map.put("optional" + j + "Fat", i++);
            map.put("optional" + j + "CarboHydreate", i++);
            map.put("optional" + j + "Sugar", i++);
        }

        map.put("sandwichId", i++);
        map.put("sandwichName", i++);
        map.put("sandwichCategory", i++);
        map.put("sandwichProtein", i++);
        map.put("sandwichFiber", i++);
        map.put("sandwichFat", i++);
        map.put("sandwichCarboHydreate", i++);
        map.put("sandwichSugar", i++);
		//Jar
		map.put("jarId", i++);
		map.put("jarName", i++);
		map.put("jarQuantity", i++);
        return map;
    }

    public boolean mapToEntity(CustomizedPackageRequest request) {

        if (request.getCustomerId() == 0) {
            throw new RuntimeException("Customer not found");
        }

        LocalTime startTime = LocalTime.of(9, 30);
        LocalTime endTime = LocalTime.of(19, 30);
        LocalTime current = LocalTime.now(ZoneId.of("Asia/Kolkata"));

        System.out.println("Current Time: " + current);

        if (current.isBefore(startTime) || current.isAfter(endTime)) {
            throw new RuntimeException("Customization allowed only between 9:30 AM to 7:30 PM");
        }

        CustomizedPackageDetails entity = new CustomizedPackageDetails();

        LocalDateTime businessDate = LocalDateTime.now().plusDays(1);
        DayOfWeek day = businessDate.getDayOfWeek();
        int weekday = day.getValue();

        Optional<CustomizedPackageDetails> data = customizedpackagedetailsRepo
                .findByCustomerIdAndCustomizedDate(request.getCustomerId(), businessDate);

        data.ifPresentOrElse(existing -> {
            customizedpackagedetailsRepo.deleteById(existing.getId());
        }, () -> System.out.println("No existing customization found for this customer & date."));

        entity.setCustomerId(request.getCustomerId());
        entity.setWeekdaysId(weekday);
        entity.setCustomizedDate(businessDate);
        entity.setIsEggAdded(request.isEggAdded());

        if (request.getEggOrSeed() != null) {
            Integer id = resolveId(request.getEggOrSeed().getId(), request.getEggOrSeed().getName());
            if (id != null) {
                entity.setEggOrSeed(id);
                entity.setEggOrSeedWeight(String.valueOf(request.getEggOrSeed().getWeight()));
            }
        }

        if (request.getSandwich() != null) {
            Integer id = resolveId(request.getSandwich().getId(), request.getSandwich().getName());
            if (id != null) entity.setSandwichId(id);
        }
			
			
			//Jar
			
			if (request.getJar() != null) {

			    Integer jarId = resolveId(
			            request.getJar().getId(),
			            request.getJar().getName()
			    );

			    if (jarId != null) {

			        entity.setJarId(jarId);

			        entity.setJarQuantity(
			                request.getJar().getWeight()
			        );
			    }
			}

			// Fruits
        List<IngredientDto> fruits = request.getFruits();
        if (fruits != null) {
            for (int i = 0; i < Math.min(fruits.size(), 6); i++) {
                IngredientDto fruit = fruits.get(i);
                Integer fruitId = resolveId(fruit.getId(), fruit.getName());
                if (fruitId == null) continue;
                String weight = fruit.getWeight();
                switch (i) {
                    case 0 -> { entity.setFruit1Id(fruitId); entity.setFruit1Weight(weight); }
                    case 1 -> { entity.setFruit2Id(fruitId); entity.setFruit2Weight(weight); }
                    case 2 -> { entity.setFruit3Id(fruitId); entity.setFruit3Weight(weight); }
                    case 3 -> { entity.setFruit4Id(fruitId); entity.setFruit4Weight(weight); }
                    case 4 -> { entity.setFruit5Id(fruitId); entity.setFruit5Weight(weight); }
                    case 5 -> { entity.setFruit6Id(fruitId); entity.setFruit6Weight(weight); }
                }
            }
        }

        List<IngredientDto> nuts = request.getNuts();
        if (nuts != null) {
            for (int i = 0; i < Math.min(nuts.size(), 5); i++) {
                IngredientDto nut = nuts.get(i);
                Integer nutId = resolveId(nut.getId(), nut.getName());
                if (nutId == null) continue;
                String weight = nut.getWeight();
                switch (i) {
                    case 0 -> { entity.setNut1Id(nutId); entity.setNut1Weight(weight); }
                    case 1 -> { entity.setNut2Id(nutId); entity.setNut2Weight(weight); }
                    case 2 -> { entity.setNut3Id(nutId); entity.setNut3Weight(weight); }
                    case 3 -> { entity.setNut4Id(nutId); entity.setNut4Weight(weight); }
                    case 4 -> { entity.setNut5Id(nutId); entity.setNut5Weight(weight); }
                }
            }
        }

        List<OptionalIngredientDto> optionalItems = request.getOptional();
        if (optionalItems != null) {
            for (int i = 0; i < Math.min(optionalItems.size(), 6); i++) {
                OptionalIngredientDto opt = optionalItems.get(i);
                Integer id = resolveId(opt.getId(), opt.getName());
                if (id == null) continue;
                String weight = opt.getWeight() != null ? opt.getWeight() : "0";
                Boolean isFruit = opt.getIsFruit() != null ? opt.getIsFruit() : false;
                switch (i) {
                    case 0 -> { entity.setOptional1Id(id); entity.setOptional1Weight(weight); entity.setOptional1IsFruit(isFruit); }
                    case 1 -> { entity.setOptional2Id(id); entity.setOptional2Weight(weight); entity.setOptional2IsFruit(isFruit); }
                    case 2 -> { entity.setOptional3Id(id); entity.setOptional3Weight(weight); entity.setOptional3IsFruit(isFruit); }
                    case 3 -> { entity.setOptional4Id(id); entity.setOptional4Weight(weight); entity.setOptional4IsFruit(isFruit); }
                    case 4 -> { entity.setOptional5Id(id); entity.setOptional5Weight(weight); entity.setOptional5IsFruit(isFruit); }
                    case 5 -> { entity.setOptional6Id(id); entity.setOptional6Weight(weight); entity.setOptional6IsFruit(isFruit); }
                }
            }
        }

        entity.setStatusId(1);
        entity.setCreatedBy("user");
        entity.setCreatedTime(LocalDateTime.now());

        CustomizedPackageDetails saved = customizedpackagedetailsRepo.save(entity);

        if (saved.getId() != null) {
            customerDetailsRepo.updateCustomization(
                    request.getCustomerId(),
                    true,
                    businessDate,
                    "user",
                    LocalDate.now()
            );
            return true;
        }
        return false;
    }

    public int getFruitOrNutId(String fruitAndNuts) {
        return lkpFruitAndNutsRepo.findByFruitAndNutsIgnoreCase(fruitAndNuts)
                .map(LkpFruitAndNuts::getId)
                .orElseThrow(() -> new RuntimeException("Fruit/Nut not found: " + fruitAndNuts));
    }

    private Integer resolveId(Long id, String name) {
        if (id != null) return id.intValue();
        if (name != null && !name.isBlank()) return getFruitOrNutId(name);
        return null;
    }

    private int getWeekday(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }

    // ============================================================
    // getTomorrowSummary — FIXED VERSION
    // ============================================================
//    public CustomizationSummaryDto getTomorrowSummary() {
//
//        // 1. Tomorrow's date
//        LocalDate tomorrow = LocalDate.now().plusDays(1);
//
//        // 2. WeekdayId — tomorrow's day value (Mon=1 ... Sun=7)
//        int weekdayId = getWeekday(tomorrow);
//
//        // Time range for querying customizations saved for tomorrow
//        LocalDateTime start = tomorrow.atStartOfDay();
//        LocalDateTime end   = tomorrow.atTime(LocalTime.MAX);
//
//        System.out.println("DEBUG Date    : " + tomorrow);
//        System.out.println("DEBUG WeekdayId: " + weekdayId);
//
//        // 3. Active customers for tomorrow (customerStatusId = 3)
//        List<CustomerDetails> allCustomers =
//                customerDetailsRepo.findActiveTomorrowCustomers();
//
//        // 4. Customizations saved for tomorrow (weekdayId + date window)
//        List<CustomizedPackageDetails> customizedList =
//                customizedpackagedetailsRepo.getTomorrowCustomizations(weekdayId, start, end);
//
//        // Map: customerId → their customization row
//        Map<Long, CustomizedPackageDetails> customizedMap =
//                customizedList.stream()
//                        .collect(Collectors.toMap(
//                                CustomizedPackageDetails::getCustomerId,
//                                c -> c,
//                                (a, b) -> a
//                        ));
//        System.out.println("DEBUG customizedMap keys: " + customizedMap.keySet());
//        System.out.println("DEBUG allCustomers ids: " + 
//            allCustomers.stream().map(CustomerDetails::getId).collect(Collectors.toList()));
//
//        // 5. Default pack rows for tomorrow's weekday (statusId=1 filtered by @Where)
//        List<CommonAndPragnentPackDetails> packRows =
//                commonandPragnentpackDetailsRepo.findByWeekdayId(weekdayId);
//
//        // Key: packageTypeId only → pack row
//        // commonandPragnentpackDetails table-la IsEggAdded column illai,
//        // so packageTypeId mattum key-a use panrom.
//        // One packageTypeId → one pack row for a weekday.
//        Map<Integer, CommonAndPragnentPackDetails> packRowMap =
//                packRows.stream()
//                        .collect(Collectors.toMap(
//                                CommonAndPragnentPackDetails::getPackageTypeId,
//                                r -> r,
//                                (a, b) -> a   // duplicate-a vandhā first row eduthuko
//                        ));
//
//        // 6. LKP lookup: id → name  (covers fruits, nuts, optionals — all in same table)
//        Map<Integer, String> lkpMap =
//                lkpFruitAndNutsRepo.findAll()
//                        .stream()
//                        .collect(Collectors.toMap(
//                                LkpFruitAndNuts::getId,
//                                LkpFruitAndNuts::getFruitAndNuts
//                        ));
//
//        // Result accumulators
//        Map<String, Long> fruitCountMap    = new HashMap<>();
//        Map<String, Long> optionalCountMap = new HashMap<>();
//
//        // 7. Loop every active customer
//        // 7a. CUSTOMIZED customers first — independent of allCustomers list
//        for (CustomizedPackageDetails cp : customizedList) {
//            List<Integer> fruitIds = Arrays.asList(
//                    cp.getFruit1Id(), cp.getFruit2Id(), cp.getFruit3Id(),
//                    cp.getFruit4Id(), cp.getFruit5Id(), cp.getFruit6Id()
//            );
//            for (Integer id : fruitIds) {
//                if (id != null && id > 0) {
//                    fruitCountMap.merge(lkpMap.getOrDefault(id, "Unknown(id=" + id + ")"), 1L, Long::sum);
//                }
//            }
//            List<Integer> optIds = Arrays.asList(
//                    cp.getOptional1Id(), cp.getOptional2Id(), cp.getOptional3Id(),
//                    cp.getOptional4Id(), cp.getOptional5Id(), cp.getOptional6Id()
//            );
//            for (Integer id : optIds) {
//                if (id != null && id > 0) {
//                    optionalCountMap.merge(lkpMap.getOrDefault(id, "Unknown(id=" + id + ")"), 1L, Long::sum);
//                }
//            }
//        }
//
//        // 7b. Non-customized active customers — default pack use pannu
//        for (CustomerDetails cd : allCustomers) {
//
//            if (customizedMap.containsKey(cd.getId())) continue; // already counted above
//
//            boolean eggPref    = Boolean.TRUE.equals(cd.isEggPreferd());
//            boolean isPragnent = Boolean.TRUE.equals(cd.isPragnent());
//            boolean isAlergic  = Boolean.TRUE.equals(cd.isAlergic());
//            boolean isCommonFlag = isCommon(isPragnent, isAlergic, false);
//
//            if (isAlergic) {
//                System.out.println("INFO: Skipping allergic customer=" + cd.getId());
//                continue;
//            }
//
//            Integer packageTypeId = PackageTypeRepo.getPackageTypeId(
//                    isPragnent, isAlergic, false, isCommonFlag, cd.getPackDetailsId()
//            ).orElse(null);
//
//            if (packageTypeId == null) continue;
//            if (packageTypeId == 12 || packageTypeId == 13) eggPref = false;
//
//            CommonAndPragnentPackDetails packRow = packRowMap.get(packageTypeId);
//            if (packRow == null) {
//                System.out.println("WARN: No pack row weekdayId=" + weekdayId
//                        + " packageTypeId=" + packageTypeId + " customer=" + cd.getId());
//                continue;
//            }
//
//            List<Integer> fruitIds = Arrays.asList(
//                    packRow.getFruit1Id(), packRow.getFruit2Id(), packRow.getFruit3Id(),
//                    packRow.getFruit4Id(), packRow.getFruit5Id(), packRow.getFruit6Id()
//            );
//            for (Integer id : fruitIds) {
//                if (id != null && id > 0) {
//                    fruitCountMap.merge(lkpMap.getOrDefault(id, "Unknown(id=" + id + ")"), 1L, Long::sum);
//                }
//            }
//
//            List<Integer> optIds = Arrays.asList(
//                    packRow.getOptional1Id(), packRow.getOptional2Id(), packRow.getOptional3Id()
//            );
//            for (Integer id : optIds) {
//                if (id != null && id > 0) {
//                    optionalCountMap.merge(lkpMap.getOrDefault(id, "Unknown(id=" + id + ")"), 1L, Long::sum);
//                }
//            }
//        }
//        // 8. Build response DTO
//        CustomizationSummaryDto dto = new CustomizationSummaryDto();
//        dto.setTotalCustomers(allCustomers.size());
//        dto.setCustomizedCustomers(customizedList.size());
//        dto.setFruitCounts(fruitCountMap);
//        dto.setOptionalCounts(optionalCountMap);
//
//        return dto;
//    }
}
