/**
 * 字符串搜索算法
 * 试图在一长字符串中，找出其是否包含某一个或多个字符串，以及其位置的算法。
 * 基本操作：给定一个长度为 M 的文本和一个长度为 N 的模式串，在文本中找到一个和该模式相符的子字符串，并返回该字字符串在文本中的位置。
 * 本类实现四种常见的字符串搜索算法
 * @author Gallon
 */ 
public class StringSearchingAlgorithms {
	
	/**
	 * 暴力搜索,浅显易懂
	 * 从左到右遍历,嵌套循环匹配,无任何跳过,效率较低
	 * @param text
	 * @param pattern
	 * @return 从左到右第一次匹配的位置
	 */
	public static int forceSearch(String text, String pattern) {
		int M = text.length();
		int N = pattern.length();
		for (int i = 0; i <= M - N; i++) {
			int j;
			for (j = 0; j < N; j++) {
				if (text.charAt(i + j) != pattern.charAt(j)) {
					break;					
				}
			}
			if (j == N) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * KMP算法
	 * KMP 算法，全称是 Knuth-Morris-Pratt 算法，以三个发明者命名，开头的那个K就是著名科学家 Donald Knuth 。
	 * KMP 算法的关键是求 next 数组。next 数组的长度为模式串的长度。
	 * next 数组中每个值代表模式串中当前字符前面的字符串中，有多大长度的相同前缀后缀。
	 * @param text
	 * @param pattern
	 * @param next
	 * @return
	 */
	public static int KMPSearch(String text, String pattern) {
		int M = text.length();
		int N = pattern.length();
		int[] next = new int[pattern.length()];
		next[0] = -1;
		int k = -1;
		int l = 0;
		while (l < N - 1) {
			if (k == -1 || pattern.charAt(l) == pattern.charAt(k)) {
				k++;
				l++;
				next[l] = k;
			} else {
				k = next[k];
			}
		}
		 
		int i = 0;
		int j = 0;
		while (i < M && j < N) {
			if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
				i++;
				j++;
			} else {
				j = next[j];
			}
		}
		if (j == N) {
			return i - j;
		} else {
			return -1;
		}
	}
	
	
	public static void main(String[] args) {
		int forceSearch = forceSearch("123123123", "31");
		System.out.println(forceSearch);
	}
}
