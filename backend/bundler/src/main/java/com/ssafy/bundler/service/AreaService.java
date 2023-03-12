package com.ssafy.bundler.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bundler.domain.Area;
import com.ssafy.bundler.repository.AreaRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AreaService {
	private final AreaRepository areaRepository;
	public Area getArea(Long userId){
		Area area = areaRepository.findByUserId(userId);
		if(area==null){
			Area newArea = Area.builder().userId(userId).job("[]").skill("[]").build();
			area = areaRepository.save(newArea);
		}

		return area;
	}

	public Area create(Area area) {
		Area oldArea = areaRepository.findByUserId(area.getUserId());
		if(oldArea!=null){
			areaRepository.deleteById(area.getUserId());
		}

		return areaRepository.save(area);
	}
}
