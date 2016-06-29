package com.syzton.sunread.service.region;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.region.RegionDTO;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.region.Region;
import com.syzton.sunread.model.region.RegionType;
import com.syzton.sunread.repository.region.RegionRepository;
import com.syzton.sunread.util.ExcelUtil;

/**
 * @author Morgan-Leon
 * @Date 2015年3月24日
 * 
 */
@Service
public class RegionRepositoryService implements RegionService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RegionRepositoryService.class);

	private RegionRepository regionRepo;

	@Autowired
	public RegionRepositoryService(RegionRepository repository) {
		// TODO Auto-generated constructor stub
		this.regionRepo = repository;
	}

	@Transactional(rollbackFor = { DuplicateException.class })
	@Override
	public Region add(RegionDTO added) {
		LOGGER.debug("Adding a new Region with information: {}", added);

		Region province = getRegion(added.getProvince(),RegionType.province,null);
		Region city = getRegion(added.getCity(),RegionType.city,province);
		city.setParent(province);
		province.getSubRegion().add(city);

		Region district = getRegion(added.getDistrict(),RegionType.district,city);
		district.setParent(city);
		city.getSubRegion().add(district);
		return regionRepo.save(district);
	}

	private Region getRegion(String regionName,RegionType regionType,Region parent){
		Region region = regionRepo.findByNameAndRegionTypeAndParent(regionName, regionType,parent);
		if (region == null){
			region = new Region();
			region.setName(regionName);
			region.setRegionType(regionType);
			regionRepo.save(region);
		}
		return region;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public void deleteById(long id) {
		LOGGER.debug("Deleting a Region with id: {}", id);

		Region deleted = findOne(id);
		if (deleted == null)
			throw new NotFoundException("No Region found with id: " + id);

		LOGGER.debug("Deleting Region entry: {}", deleted);
		regionRepo.delete(deleted);
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Region update(Region updated,long id) {
		LOGGER.debug("Updating contact with information: {}", updated);

		Region model = findOne(id);

		LOGGER.debug("Found a note entry: {}", model);
		model.setRegionType(updated.getRegionType());
		model.setName(updated.getName());
		regionRepo.save(model);
		return updated;
	}

	@Override
	public Region findOne(Long id) {
		LOGGER.debug("Finding a Region with id: {}", id);

		Region found = regionRepo.findOne(id);
		LOGGER.debug("Found Region entry: {}", found);

		if (found == null) {
			throw new NotFoundException("No Region found with id: " + id);
		}
		return found;
	}

	@Override
	public Page<Region> findProvinces(Pageable pageable) {
		LOGGER.debug("Finding all Region entries");
		Page<Region> provinces = regionRepo.findByRegionType(pageable,RegionType.province);
		if (provinces == null) {
			throw new NotFoundException("No Region found");
		}
		return provinces;
	}
	
	@Transactional
	@Override
	public Map<Integer, String> batchSaveOrUpdateRegionFromExcel(Sheet sheet) {
		Map<Integer, String> failMap = new HashMap<Integer, String>();

		for (int i = sheet.getFirstRowNum()+1; i < sheet
				.getPhysicalNumberOfRows(); i++) {
			Row row = sheet.getRow(i);

			String province = ExcelUtil.getStringFromExcelCell(row.getCell(0));
			Region provinceRegion = regionRepo.findByNameAndRegionTypeAndParent(province, RegionType.province, null);
			if(provinceRegion == null){
				provinceRegion = new Region();
				provinceRegion.setName(province);
				provinceRegion.setParent(null);
				provinceRegion.setRegionType(RegionType.province);
				provinceRegion = regionRepo.save(provinceRegion);
			}
			String city = ExcelUtil.getStringFromExcelCell(row.getCell(1));
			Region cityRegion = regionRepo.findByNameAndRegionTypeAndParent(city, RegionType.city, provinceRegion);
			if(cityRegion == null){
				cityRegion = new Region();
				cityRegion.setName(city);
				cityRegion.setParent(provinceRegion);
				cityRegion.setRegionType(RegionType.city);
				cityRegion = regionRepo.save(cityRegion);
				Set<Region> regions = provinceRegion.getSubRegion();
				if(regions == null){
					regions = new HashSet<Region>();
				}
				regions.add(cityRegion);
				provinceRegion.setSubRegion(regions);
				regionRepo.save(provinceRegion);
			}
			
			String district = ExcelUtil.getStringFromExcelCell(row.getCell(2));
			Region districtRegion = regionRepo.findByNameAndRegionTypeAndParent(district, RegionType.district, cityRegion);
			if(districtRegion == null){
				districtRegion = new Region();
				districtRegion.setName(district);
				districtRegion.setParent(cityRegion);
				districtRegion.setRegionType(RegionType.district);
				cityRegion = regionRepo.save(districtRegion);
				Set<Region> regions = cityRegion.getSubRegion();
				if(regions == null){
					regions = new HashSet<Region>();
				}
				regions.add(districtRegion);
				cityRegion.setSubRegion(regions);
				regionRepo.save(cityRegion);
			}
			
		
		}
		return failMap;
	}

}
