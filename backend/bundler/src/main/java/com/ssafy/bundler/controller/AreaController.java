package com.ssafy.bundler.controller;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bundler.domain.Area;
import com.ssafy.bundler.dto.area.AreaDto;
import com.ssafy.bundler.service.AreaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/area")
public class AreaController {
	private final AreaService areaService;
	@GetMapping(value = "/{userId}")
	public ResponseEntity<?> get(@PathVariable("userId") Long userId){
		Area userArea =  areaService.getArea(userId);
		String[] job = getArrayFromString(userArea.getJob());
		String[] skill = getArrayFromString(userArea.getSkill());
		AreaDto area = new AreaDto(userArea.getUserId(),job,skill);

		return ResponseEntity.ok(area);
	}
	@PostMapping
	public ResponseEntity<?> post(@RequestBody AreaDto area){
		Area newArea  = Area.builder()
			.userId(area.getUserId())
			.job(Arrays.toString(area.getJob()))
			.skill(Arrays.toString(area.getSkill()))
			.build();
		areaService.create(newArea);
		return ResponseEntity.ok("create");
	}

	String[] getArrayFromString(String str){
		return  str.substring(1,str.length()-1).split(", ");
	}
}
