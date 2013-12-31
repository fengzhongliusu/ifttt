//reference: examples in Sina weibo sdk

package assist;



import weibo4j.Timeline;
import weibo4j.http.HttpClient;
import weibo4j.model.PostParameter;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import assist.WeiboException;

/**
 * This class contains necessary functions when weibo is used.
 */
public class Weibo {
	
	private String access_token;
	private HttpClient client;

	// initial
	public Weibo() {
		
		access_token = "2.009TBlsC2YobVB90a2c94756072mSa";
		client = new HttpClient();
		client.setToken(access_token);
	}
	
	// update a weibo
	public void update(String content) throws WeiboException {
		
		PostParameter[] params = new PostParameter[] { new PostParameter(
				"status", content) };
		String apiUrl = "https://api.weibo.com/2/statuses/update.json";
		post(apiUrl, params);
	}

	// get user token info
	public void getTokenInfo() throws WeiboException {
		PostParameter[] params = new PostParameter[] { new PostParameter(
				"access_token", access_token) };
		String apiUrl = "https://api.weibo.com/oauth2/get_token_info";
		System.out.println("token info: " + post(apiUrl, params));
	}

	/**
	 * @return latest status text. null when error
	 * @throws WeiboException
	 */
	public Status getLatestStatus() throws WeiboException{
		Status latestStatus = null;
		Timeline tm = new Timeline();
		tm.client.setToken(access_token);
		try {
			StatusWapper status = tm.getUserTimeline();
			status.getStatuses().get(0);
			latestStatus = status.getStatuses().get(0);
			
		} catch (weibo4j.model.WeiboException e) {
			throw new WeiboException();
		}
		return latestStatus;
	}
	
	
	// private method: send a http post to url using params
	private String post(String url, PostParameter[] params) throws WeiboException {

		String response;
		try {
			response = client.post(url, params).asString();
		} catch (weibo4j.model.WeiboException e) {
			throw new WeiboException();
		}
		return response;
	}

}
