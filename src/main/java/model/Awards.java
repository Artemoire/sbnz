package model;

import java.io.Serializable;

public class Awards implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean awardedDiamondCoupon;
	private boolean awardedReturningCoupon;

	public Awards() {
		awardedDiamondCoupon = false;
		awardedReturningCoupon = false;
	}
	
	

	public boolean isAwardedDiamondCoupon() {
		return awardedDiamondCoupon;
	}

	public void setAwardedDiamondCoupon(boolean awardedDiamondCoupon) {
		this.awardedDiamondCoupon = awardedDiamondCoupon;
	}

	public boolean isAwardedReturningCoupon() {
		return awardedReturningCoupon;
	}

	public void setAwardedReturningCoupon(boolean awardedReturningCoupon) {
		this.awardedReturningCoupon = awardedReturningCoupon;
	}

}
