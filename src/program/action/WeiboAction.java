package program.action;

import assist.WeiboException;
import assist.Weibo;

/**
 * The specific action of update a weibo. 
 */
public class WeiboAction extends Action {

	protected String content;
	protected Weibo weibo;//use this weibo to tweet
	
	public WeiboAction(String weiboContent) {
		super(WEIBO_ACTION);
		weibo = new Weibo();
		content = weiboContent;
	}
	
	/**
	 * update a weibo.
	 */
	@Override
	public void Do() throws WeiboException {
		weibo.getTokenInfo();
		//weibo.update(content);
		System.out.println("update a weibo successfully");
	}

	@Override
	public String[] getParams() {
		String[] params = {content};
		return params;
	}

	@Override
	public String getDescription() {
		return "Update a weibo";
	}
}
