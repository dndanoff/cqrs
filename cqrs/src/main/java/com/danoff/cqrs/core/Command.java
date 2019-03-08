package com.danoff.cqrs.core;

import java.time.LocalDateTime;

public interface Command<T> extends DomainObject{

	LocalDateTime getCreatedAt();
	T getData();
}
