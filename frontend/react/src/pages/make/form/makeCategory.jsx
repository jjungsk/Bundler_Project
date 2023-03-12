// Import - react
import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";

// Import - @mui/material
import { Box, Typography, MenuItem, FormControl, Select } from "@mui/material";
// import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";

function MakeCategory({ handleCategory }) {
  const [value, setValue] = useState({
    first: 1,
    second: 6,
  });

  const handleChangeOne = (event) => {
    event.preventDefault();
    const firstCat = event.target.value;
    let secondCat = 0;
    if (firstCat === 1) {
      secondCat = 6;
    } else if (firstCat === 2) {
      secondCat = 11;
    } else if (firstCat === 3) {
      secondCat = 18;
    } else if (firstCat === 4) {
      secondCat = 24;
    } else if (firstCat === 5) {
      secondCat = 31;
    }
    handleCategory(event, secondCat);
    setValue({
      first: firstCat,
      second: secondCat,
    });
  };

  const handleChangeTwo = (event) => {
    event.preventDefault();
    handleCategory(event, event.target.value);
    setValue({ ...value, second: event.target.value });
  };

  useEffect(() => {}, [value]);

  return (
    <Box mt={2}>
      <Typography variant="h6">카테고리 선택</Typography>
      <FormControl sx={{ mt: 2, mr: 2, minWidth: 120 }}>
        <Select
          required
          labelId="card-category-one-label"
          id="card-category-one"
          value={value.first}
          onChange={handleChangeOne}
          autoWidth
          sx={{ height: "50px" }}
          // IconComponent={<ArrowDropDownIcon />}
        >
          <MenuItem value={1}>알고리즘</MenuItem>
          <MenuItem value={2}>CS</MenuItem>
          <MenuItem value={3}>직무</MenuItem>
          <MenuItem value={4}>언어</MenuItem>
          <MenuItem value={5}>기타</MenuItem>
        </Select>
      </FormControl>
      {value.first === 1 && (
        <FormControl sx={{ mt: 2, minWidth: 120 }}>
          <Select
            labelId="card-category-two-label"
            id="card-category-two"
            value={value.second}
            onChange={handleChangeTwo}
            autoWidth
            displayEmpty
            sx={{ height: "50px" }}
          >
            <MenuItem value={6}>그래프 탐색</MenuItem>
            <MenuItem value={7}>스택/큐/정렬</MenuItem>
            <MenuItem value={8}>완전탐색/이분탐색</MenuItem>
            <MenuItem value={9}>탐욕/동적계획법/해시</MenuItem>
            <MenuItem value={10}>기타</MenuItem>
          </Select>
        </FormControl>
      )}
      {value.first === 2 && (
        <FormControl sx={{ mt: 2, minWidth: 120 }}>
          <Select
            labelId="card-category-two-label"
            id="card-category-two"
            value={value.second}
            onChange={handleChangeTwo}
            autoWidth
            displayEmpty
            sx={{ height: "50px" }}
          >
            <MenuItem value={11}>수학</MenuItem>
            <MenuItem value={12}>컴구</MenuItem>
            <MenuItem value={13}>운영체제</MenuItem>
            <MenuItem value={14}>자구</MenuItem>
            <MenuItem value={15}>네트워크</MenuItem>
            <MenuItem value={16}>데이터 베이스</MenuItem>
            <MenuItem value={17}>기타</MenuItem>
          </Select>
        </FormControl>
      )}
      {value.first === 3 && (
        <FormControl sx={{ mt: 2, minWidth: 120 }}>
          <Select
            labelId="card-category-two-label"
            id="card-category-two"
            value={value.second}
            onChange={handleChangeTwo}
            autoWidth
            displayEmpty
            sx={{ height: "50px" }}
          >
            <MenuItem value={18}>면접</MenuItem>
            <MenuItem value={19}>백엔드</MenuItem>
            <MenuItem value={20}>프론트엔드</MenuItem>
            <MenuItem value={21}>인프라</MenuItem>
            <MenuItem value={22}>DBA</MenuItem>
            <MenuItem value={23}>기타</MenuItem>
          </Select>
        </FormControl>
      )}
      {value.first === 4 && (
        <FormControl sx={{ mt: 2, minWidth: 120 }}>
          <Select
            labelId="card-category-two-label"
            id="card-category-two"
            value={value.second}
            onChange={handleChangeTwo}
            autoWidth
            displayEmpty
            sx={{ height: "50px" }}
          >
            <MenuItem value={24}>C/C++</MenuItem>
            <MenuItem value={25}>Python</MenuItem>
            <MenuItem value={26}>JAVA</MenuItem>
            <MenuItem value={27}>Kotlin</MenuItem>
            <MenuItem value={28}>Js/Html/Css</MenuItem>
            <MenuItem value={29}>SQL</MenuItem>
            <MenuItem value={30}>기타</MenuItem>
          </Select>
        </FormControl>
      )}
      {value.first === 5 && (
        <FormControl sx={{ mt: 2, minWidth: 120 }}>
          <Select
            labelId="card-category-two-label"
            id="card-category-two"
            value={value.second}
            onChange={handleChangeTwo}
            autoWidth
            displayEmpty
            sx={{ height: "50px" }}
          >
            <MenuItem value={31}>기업 분석</MenuItem>
            <MenuItem value={32}>IT기사/트렌드</MenuItem>
            <MenuItem value={33}>자격증</MenuItem>
            <MenuItem value={34}>기타</MenuItem>
          </Select>
        </FormControl>
      )}
    </Box>
  );
}

MakeCategory.propTypes = {
  handleCategory: PropTypes.func.isRequired,
};

export default MakeCategory;
