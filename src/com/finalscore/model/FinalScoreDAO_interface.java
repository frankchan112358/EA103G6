package com.finalscore.model;

import java.util.*;

public interface FinalScoreDAO_interface {

	public void insert(FinalScoreVO finalScoreVO);

	public void update(FinalScoreVO finalScoreVO);

	public void delete(String finalScoreNo);

	public FinalScoreVO findByPrimaryKey(String finalScoreNo);

	public List<FinalScoreVO> getAll();
}
