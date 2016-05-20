package de.itemis.infinispan.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

@WebServlet("/")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/infinispan/container/TEST")
	private EmbeddedCacheManager cacheManager;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = new PrintWriter(response.getWriter());
		pw.println("<!DOCTYPE html><html><head><title>Cache Test</title></head><body>");
		pw.println("<h1>Cache Test</h1>");

		pw.println("<p>");
		pw.println("Cache names before starting a cache: " + cacheManager.getCacheNames() + "<br>");
		pw.println("</p>");

		pw.println("<p>");
		Cache<Object, Object> defaultCache = cacheManager.getCache();
		pw.println("Getting default cache from cache manager: " + defaultCache + "<br>");
		if (defaultCache != null) {
			pw.println("<b>Default Cache:</b><br>");
			pw.println("<ul>");
			pw.println("<li>Name: " + defaultCache.getName() + "</li><br>");
			pw.println("<li>Eviction max entries (should be 3): "
					+ defaultCache.getCacheConfiguration().eviction().maxEntries() + "</li><br>");
			pw.println("<li>Eviction strategy (should be LRU): "
					+ defaultCache.getCacheConfiguration().eviction().strategy() + "</li><br>");
			pw.println("</ul>");
		}
		pw.println("</p>");

		pw.println("<p>");
		pw.println("Getting cache y from cache manager (don't create if not existing): "
				+ cacheManager.getCache("y", false) + "<br>");
		Cache<Object, Object> y = cacheManager.getCache("y", true);
		pw.println("Getting cache y from cache manager: " + y + "<br>");
		if (y != null) {
			pw.println("<b>Cache y:</b><br>");
			pw.println("<ul>");
			pw.println("<li>Name: " + y.getName() + "</li><br>");
			pw.println("<li>Eviction max entries (should be 5): " + y.getCacheConfiguration().eviction().maxEntries()
					+ "</li><br>");
			pw.println("<li>Eviction strategy (should be FIFO): " + y.getCacheConfiguration().eviction().strategy()
					+ "</li><br>");
			pw.println("</ul>");
		}
		pw.println("</p>");

		pw.println("<p>");
		Cache<Object, Object> z = cacheManager.getCache("z", true);
		pw.println("Now try to get a cache that isn't event configured in the infinispan subsystem: " + z + "<br>");
		if (z != null) {
			pw.println("<b>Cache z:</b><br>");
			pw.println("<ul>");
			pw.println("<li>Name: " + z.getName() + "</li><br>");
			pw.println("<li>Eviction max entries: " + z.getCacheConfiguration().eviction().maxEntries() + "</li><br>");
			pw.println("<li>Eviction strategy: " + z.getCacheConfiguration().eviction().strategy() + "</li><br>");
			pw.println("</ul>");
		}
		pw.println("</p>");

		pw.println("<p>");
		pw.println("Cache names after retrieving some caches:" + cacheManager.getCacheNames() + "<br>");
		pw.println("</p>");

		pw.println("</body></html>");
	}
}