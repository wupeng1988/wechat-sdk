package org.dptech.wap.train.persist.model;

import java.util.Date;

public class TimeManaged {

	Date createTime = new Date();

	Date updateTime = new Date();

	Long updateTimestamp = System.currentTimeMillis();

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Long updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

}
