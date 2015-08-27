package com.jtouzy.cv.model.tools.back;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

public class DropboxAPI {
	
	public static void downloadDumpFile()
	throws IOException, DbxException {
		System.out.println("Téléchargement du fichier depuis Dropbox...");
		DbxClient client = DropboxAPI.getClientByToken("CantalVolley", "lntoAdw3MMQAAAAAAAAF1FKLBpmxtHmOmMq_Ev1mUX1HuiirRS4m1sD8KNkqMc3P");
		/*DbxClient client = DropboxAPI.getClient("mywypxqmd286kg1", "3xcn95yi2jluokv", "CantalVolley");*/
		FileOutputStream outputStream = new FileOutputStream("dbdump.xml");
		try {
		    client.getFile("/Développements/CantalVolley/dbdump.xml", null, outputStream);
		    outputStream.flush();
		} finally {
		    outputStream.close();
		}
	}
	
	public static DbxClient getClientByToken(String requestId, String token) {
		DbxRequestConfig config = new DbxRequestConfig(requestId, Locale.getDefault().toString());
		DbxClient client = new DbxClient(config, token);
		return client;
	}
	
	public static DbxClient getClient(String dropboxApplicationKey, String secretApplicationKey, String requestId) {
		try {
			DbxAppInfo appInfo = new DbxAppInfo(dropboxApplicationKey, secretApplicationKey);
			DbxRequestConfig config = new DbxRequestConfig(requestId, Locale.getDefault().toString());
			DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
			String authorizeUrl = webAuth.start();
			System.out.println("1. Go to: " + authorizeUrl);
			System.out.println("2. Click \"Allow\" (you might have to log in first)");
			System.out.println("3. Copy the authorization code.");
			String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
			DbxAuthFinish authFinish = webAuth.finish(code);
			String accessToken = authFinish.accessToken;
			DbxClient client = new DbxClient(config, accessToken);
			System.out.println("Linked account: " + client.getAccountInfo().displayName);
			return client;
		} catch (DbxException | IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}