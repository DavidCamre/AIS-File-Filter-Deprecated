package dk.dma.ais;

import java.util.Date;

import dk.dma.ais.filter.ExpressionFilter;

public class AisFilter {

	
	
	private ExpressionFilter expressionFilter;
	private String type;
	private Date timeStart;
	private Date timeEnd;

	private int messageId;

	public AisFilter(ExpressionFilter expressionFilter, String name) {
		super();
		this.expressionFilter = expressionFilter;
		this.type = name;
	}

	public AisFilter(ExpressionFilter expressionFilter, String type, int messageId) {
		super();
		this.expressionFilter = expressionFilter;
		this.type = type;
		this.messageId = messageId;
	}

	public AisFilter(Date timeStart, Date timeEnd, String type) {
		super();
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.type = type;
	}

	public ExpressionFilter getExpressionFilter() {
		return expressionFilter;
	}

	public void setExpressionFilter(ExpressionFilter expressionFilter) {
		this.expressionFilter = expressionFilter;
	}

	public String getName() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	
	

}
