package edu.les.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

@Deprecated
public class AddressAPI {
	public static Map<String, String> getViaCepAPIAddress(String cep) {
		String pipedAddress = "";
		try {
			final String uri = "https://viacep.com.br/ws/" + cep + "/piped";
			RestTemplate restTemplate = new RestTemplate();
			pipedAddress = restTemplate.getForObject(uri, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AddressAPI.convertStringToHashMap(pipedAddress);
	}

	private static Map<String, String> convertStringToHashMap(
			String jsonAsString) {
		Map<String, String> map = new HashMap<String, String>();
		String[] keyValue = jsonAsString.split("\\|");
		for (int i = 0; i < keyValue.length; ++i) {
			try {
				String[] pair = keyValue[i].split(":");
				map.put(pair[0], pair[1]);
			} catch (Exception e) {
				System.out.println("unable to split piped string");
			}
		}
		return map;
	}
}
