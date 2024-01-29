package aws.test.utils;

import java.time.LocalDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SingletonGson {
	private static Gson singleInstance;

	public static Gson getInstance() {
		if (singleInstance == null) {
			synchronized (SingletonGson.class) {
				if (singleInstance == null) {
					singleInstance = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
							.create();
				}
			}
		}
		return singleInstance;
	}
}
