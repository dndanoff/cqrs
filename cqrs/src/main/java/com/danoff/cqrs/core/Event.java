package com.danoff.cqrs.core;

import java.time.LocalDateTime;

public interface Event<T> extends DomainObject{
	
	String getId();
	Long getVersion();
	LocalDateTime getCreatedAt();
	String getAggregateId();
	T getData();
}
