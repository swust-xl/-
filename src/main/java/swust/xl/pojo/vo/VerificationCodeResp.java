package swust.xl.pojo.vo;

/**
 * 获取验证图片响应体
 * 
 * @author xuLiang
 * @since 0.0.1
 */
public class VerificationCodeResp {
	private String cuttedImgBase64;
	private String cuttedOriginImgBase64;
	private String XCoordinate;
	private String YCoordinate;

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

	public String getXCoordinate() {
		return XCoordinate;
	}

	public void setXCoordinate(String xCoordinate) {
		XCoordinate = xCoordinate;
	}

	public String getYCoordinate() {
		return YCoordinate;
	}

	public void setYCoordinate(String yCoordinate) {
		YCoordinate = yCoordinate;
	}
}
