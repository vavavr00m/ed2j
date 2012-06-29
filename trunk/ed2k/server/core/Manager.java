package ed2k.server.core;

import java.util.Collection;

import cc.gpai.data_stru.multimap.MultiDMap;
import cc.gpai.data_stru.multimap.impl.LinkedHashMultiDMap;

import ed2k.server.client.Client;
import ed2k.server.data_stru.FileHash;

public class Manager {

	private static final MultiDMap<Client,FileHash> map = new LinkedHashMultiDMap<Client,FileHash>();
	
	public static void addRes(Client c,Collection<FileHash> files){
		map.putValues(c, files);
	}
	public static void addRes(Client c,FileHash file){
		map.put(c, file);
	}
	public static void delRes(Client c){
		map.remove(c);
	}
	public static Collection<Client> findClients(FileHash file){
		return map.getKeys(file);
	}
}
