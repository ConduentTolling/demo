package com.conduent.tpms.process25a.dao;

import com.conduent.tpms.process25a.model.XferControl;

public interface IXferControlDao {
	public XferControl getXferControlDate(Long externFileId);

}
