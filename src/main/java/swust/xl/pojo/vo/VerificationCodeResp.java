package swust.xl.pojo.vo;

/**
 * 验证图片响应体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class VerificationCodeResp {

	private String cuttedImgBase64;
	private String cuttedOriginImgBase64;
	private String xCoordinate;
	private String yCoordinate;

	@Override
	public String toString() {
		return "VerificationCodeResp [cuttedImgBase64=" + cuttedImgBase64 + ", cuttedOriginImgBase64="
				+ cuttedOriginImgBase64 + ", XCoordinate=" + xCoordinate + ", YCoordinate=" + yCoordinate + "]";
	}

	public String getCuttedImgBase64() {
		return cuttedImgBase64;
	}

	public void setCuttedImgBase64(String cuttedImgBase64) {
		this.cuttedImgBase64 = cuttedImgBase64;
	}

	public String getCuttedOriginImgBase64() {
		return cuttedOriginImgBase64;
	}

	public void setCuttedOriginImgBase64(String cuttedOriginImgBase64) {
		this.cuttedOriginImgBase64 = cuttedOriginImgBase64;
	}

	public String getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(String xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public String getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(String yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

}
