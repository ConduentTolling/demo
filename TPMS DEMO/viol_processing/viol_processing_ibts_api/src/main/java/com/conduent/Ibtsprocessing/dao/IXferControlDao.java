package com.conduent.Ibtsprocessing.dao;

import java.util.List;

import com.conduent.Ibtsprocessing.model.XferControl;

public interface IXferControlDao {
	public XferControl getXferControlDate(Long externFileId);

}
