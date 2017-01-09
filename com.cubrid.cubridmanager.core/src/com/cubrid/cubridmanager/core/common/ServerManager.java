/*
 * Copyright (C) 2012 Search Solution Corporation. All rights reserved by Search
 * Solution.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: -
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. - Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. - Neither the name of the <ORGANIZATION> nor the names
 * of its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */

package com.cubrid.cubridmanager.core.common;

import java.util.Iterator;
import java.util.List;

import com.cubrid.common.ui.spi.model.CubridServer;
import com.cubrid.cubridmanager.core.common.model.ServerInfo;
import com.cubrid.cubridmanager.ui.spi.model.loader.CubridServerLoader;
import com.cubrid.cubridmanager.ui.spi.persist.CMHostNodePersistManager;

/**
 * This class is responsible to manage all CUBRID Manager server
 * 
 * @author pangqiren
 * @version 1.0 - 2009-6-4 created by pangqiren
 */
public final class ServerManager {

	private static ServerManager manager = new ServerManager();

	private ServerManager() {
	}

	/**
	 * Return the only CUBRID Manager server manager instance
	 * 
	 * @return ServerManager
	 */
	public static ServerManager getInstance() {
		return manager;
	}

	/**
	 * Return connected status of server
	 * 
	 * @param hostAddress String host address
	 * @param port int host port
	 * @param userName the String
	 * @return boolean
	 */
	public boolean isConnected(String hostAddress, int port, String userName) {
		ServerInfo serverInfo = getServer(hostAddress, port, userName);
		if (serverInfo == null) {
			return false;
		}
		return serverInfo.isConnected();
	}

	/**
	 * Set connected status of server
	 * 
	 * @param hostAddress String host address
	 * @param port int host port
	 * @param userName the String
	 * @param isConnected boolean whether is connected
	 */
	public void setConnected(String hostAddress, int port, String userName, boolean isConnected) {
		synchronized (this) {
			ServerInfo serverInfo = getServer(hostAddress, port, userName);
			if (serverInfo == null) {
				return;
			}
			serverInfo.setConnected(isConnected);
//			if (!isConnected) {
//				serverInfoMap.remove(hostAddress + ":" + port + ":" + userName);
//			}
		}
	}

	/**
	 * Get CUBRID Manager server information
	 * 
	 * @param hostAddress String host address
	 * @param port int host port
	 * @param userName the String
	 * @return ServerInfo
	 */
	public ServerInfo getServer(String hostAddress, int port, String userName) {
		List<CubridServer> servers = CMHostNodePersistManager.getInstance().getAllServer();
		for(CubridServer server : servers){
			if(server.getServerInfo().getHostAddress().compareTo(hostAddress) == 0 &&
					server.getServerInfo().getHostMonPort() == port &&
					server.getServerInfo().getUserName().compareTo(userName) == 0){
				return server.getServerInfo();
			}
		}
		return null;
	}

	/**
	 * Remove CUBRID Manager server
	 * 
	 * @param hostAddress String host address
	 * @param port int host port
	 * @param userName the String
	 */
	public void removeServer(String hostAddress, int port, String userName) {
		synchronized (this) {
			setConnected(hostAddress, port, userName, false);
			Iterator<CubridServer> servers = CMHostNodePersistManager.getInstance().getAllServer().iterator();
			while(servers.hasNext()){
				CubridServer server = (CubridServer)servers.next();
				if(server.getServerInfo().getServerName().compareTo(hostAddress) == 0 &&
						server.getServerInfo().getHostMonPort() == port &&
						server.getServerInfo().getUserName().compareTo(userName) == 0){
					servers.remove();
				}
			}
		}
	}

	/**
	 * Add CUBRID Manager server information
	 * 
	 * @param hostAddress String host address
	 * @param port int host port
	 * @param value ServerInfo given serverInfo
	 * @param userName the String
	 * @return ServerInfo
	 */
	public void addServer(String hostAddress, int port, String userName, ServerInfo value) {
		synchronized (this) {
			CubridServer server = CMHostNodePersistManager.getInstance().getServer(hostAddress, port, userName);
			if(server == null){
				CubridServer newServer = new CubridServer(value.getServerName(),
						value.getServerName(), "com.cubrid.cubridmananger.ui/icons/navigator/host.png",
						"com.cubrid.cubridmananger.ui/icons/navigator/host_connected.png");
				newServer.setServerInfo(value);
				newServer.setLoader(new CubridServerLoader());
				CMHostNodePersistManager.getInstance().addServer(newServer);
			}else{
				server.setServerInfo(value);
			}
		}
	}

	public void disConnectAllServer() {
		synchronized (this) {
			Iterator<CubridServer> servers = CMHostNodePersistManager.getInstance().getAllServer().iterator();
			while (servers.hasNext()) {
				ServerInfo serverInfo = servers.next().getServerInfo();
				if (serverInfo.isConnected()) {
					setConnected(serverInfo.getHostAddress(), serverInfo.getHostMonPort(),
							serverInfo.getLoginedUserInfo().getUserName(), false);
				}
			}
		}
	}
	
	public List<CubridServer> getAllServers(){
		return CMHostNodePersistManager.getInstance().getAllServer();
	}

}
