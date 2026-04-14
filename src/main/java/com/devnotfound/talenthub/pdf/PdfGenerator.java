package com.devnotfound.talenthub.pdf;

public interface PdfGenerator<T> {
	byte[] generate(T data);

}