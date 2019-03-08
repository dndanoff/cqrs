package com.danoff.cqrs.core;

public abstract class BaseAggregateRoot<T> implements AggregateRoot{
	
	private String id;
	private Long version;
	private T state;
	
	public BaseAggregateRoot(String id) {
		this(id, 0L, null);
	}
	
	public BaseAggregateRoot(String id, Long version) {
		this(id, version, null);
	}

	public BaseAggregateRoot(String id, Long version, T state) {
		super();
		this.id = id;
		this.version = version;
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public T getState() {
		return state;
	}
	
	public AggregateRoot setVersion(Long version) {
		this.version = version;
		return this;
	}

	public abstract BaseAggregateRoot<T> apply(Event<?> event);

}
