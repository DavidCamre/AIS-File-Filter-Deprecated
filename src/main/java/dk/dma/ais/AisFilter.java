package dk.dma.ais;

import java.util.Date;

import dk.dma.ais.filter.ExpressionFilter;

public class AisFilter {

	private ExpressionFilter expressionFilter;
	private String name;
	private Date timeStart;
	private Date timeEnd;
	private FilterTypes type;

	private int messageId;

	public AisFilter(ExpressionFilter expressionFilter, String name, FilterTypes type) {
		super();
		this.expressionFilter = expressionFilter;
		this.name = name;
		this.type = type;
	}

	public AisFilter(ExpressionFilter expressionFilter, String name, int messageId, FilterTypes type) {
		super();
		this.expressionFilter = expressionFilter;
		this.name = name;
		this.messageId = messageId;
		this.type = type;
	}

	public AisFilter(Date timeStart, Date timeEnd, String name) {
		super();
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.name = name;
		this.type = FilterTypes.Time;
	}

	public ExpressionFilter getExpressionFilter() {
		return expressionFilter;
	}

	public void setExpressionFilter(ExpressionFilter expressionFilter) {
		this.expressionFilter = expressionFilter;
	}

	public String getName() {
		return name;
	}

	public void setType(String type) {
		this.name = type;
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
