package com.conduent.Ibtsprocessing.dao;

import com.conduent.Ibtsprocessing.dto.IbtsViolProcessDTO;

public interface IQueueDao {

	public void insertInToQueue(IbtsViolProcessDTO commonDTO);

}
