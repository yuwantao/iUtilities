package com.httpclient;

import java.math.BigDecimal;

/**
 * Created by yuwt on 2016/12/6.
 */
public class KongfzBookInfo implements Comparable<KongfzBookInfo>{
	private String bookId;
	private String shopId;
	private BigDecimal price;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	@Override
	public int compareTo(KongfzBookInfo o) {
		return this.price.compareTo(o.price);
	}
}
