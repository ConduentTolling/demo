package com.conduent.tpms.inrx.dao;

import com.conduent.tpms.inrx.model.XferControl;

public interface IXferControlDao {
	public XferControl getXferControlDate(Long externFileId);

}
