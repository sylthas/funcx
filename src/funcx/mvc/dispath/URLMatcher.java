package funcx.mvc.dispath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import funcx.mvc.exception.URLMatchException;

/**
 * URL匹配器
 * 
 * <p>
 * 要实现RESTful风格的URL需要映射URL中的参数<br/>
 * 参数位置使用 $1...$9 代替<br/>
 * 
 * @author Sylthas
 * 
 */
public final class URLMatcher {

	static final String PARAM_PATTERN = "([^\\/]*)";
	static final String SAFE_CHARS = "/$-_.+!*'(),";
	static final String[] EMPTY_PARAM = new String[0];

	final String url;
	Pattern pattern;
	int[] paramOrder;

	public URLMatcher(String url) {
		this.url = url;
		List<Integer> paramList = new ArrayList<Integer>();
		Set<Integer> paramSet = new HashSet<Integer>();
		StringBuilder bd = new StringBuilder(url.length() + 40);
		bd.append('^');
		int pos = 0;
		int len = url.length();
		while (true) {
			// 查找参数位置 $x
			int index = url.indexOf('$', pos);
			if (index != -1 && index < len - 1
					&& isParamIndex(url.charAt(index + 1))) {
				// 获取当前参数的索引
				int n = url.charAt(index + 1) - '0';
				paramList.add(n);
				paramSet.add(n);
				exactMatch(bd, url.substring(pos, index));
				bd.append(PARAM_PATTERN);
				pos = index + 2;
			} else {
				// 未找到 $x
				exactMatch(bd, url.substring(pos, url.length()));
				break;
			}
		}
		// 检查参数 HashSet集合是唯一的添加的值不会重复 ArrayList是有序的，可以争取记录参数索引位置
		if (paramList.size() != paramSet.size())
			throw new URLMatchException("Duplicate parameters.");
		for (int i = 1; i <= paramSet.size(); i++) {
			if (!paramSet.contains(i))
				throw new URLMatchException("Missing parameter '$" + i + "'.");
		}
		// 记录参数索引顺序
		this.paramOrder = new int[paramList.size()];
		for (int i = 0; i < paramList.size(); i++) {
			this.paramOrder[i] = paramList.get(i) - 1;
		}
		bd.append('$');
		this.pattern = Pattern.compile(bd.toString());
	}

	// 参数索引范围
	boolean isParamIndex(char c) {
		return c > '0' && c <= '9';
	}

	// 精确匹配
	void exactMatch(StringBuilder bd, String s) {
		for (int i = 0, len = s.length(); i < len; i++) {
			char c = s.charAt(i);
			if (c >= 'a' && c <= 'z')
				bd.append(c);
			else if (c >= 'A' && c <= 'Z')
				bd.append(c);
			else if (c >= '0' && c <= '9')
				bd.append(c);
			else {
				int n = SAFE_CHARS.indexOf(c);
				// 存在不安全字符 须转换
				if (n < 0) {
					bd.append("\\u").append(Integer.toHexString(c));
				} else {
					bd.append('\\').append(c);
				}
			}
		}
	}

	/**
	 * 获取参数长度
	 * 
	 * @return
	 */
	public int getArgsLength() {
		return this.paramOrder.length;
	}

	/**
	 * 获取URL正则表达式
	 * 
	 * <p>
	 * 方便检查错误时使用,很多时候是URL中一个字符错位
	 * 
	 * @return
	 */
	public Pattern getURLPattern() {
		return this.pattern;
	}

	/**
	 * 获取匹配URL的参数
	 * 
	 * @param url
	 * @return
	 */
	public String[] getMatchParams(String url) {
		Matcher m = pattern.matcher(url);
		int len = paramOrder.length;
		if (!m.matches())
			return null;
		if (len == 0)
			return EMPTY_PARAM;
		String[] params = new String[len];
		for (int i = 0; i < len; i++) {
			params[paramOrder[i]] = m.group(i + 1);
		}
		return params;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof URLMatcher) {
			return ((URLMatcher) obj).url.equals(this.url);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}
}
