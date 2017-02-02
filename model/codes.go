package model

type Code uint8

const (
	OK Code = 1 + iota
	NOT_FOUND
	DEPENDENCY_CONNECTION_ERROR
	ERROR
)
