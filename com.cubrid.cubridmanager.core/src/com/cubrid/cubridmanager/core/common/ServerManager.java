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

import java.util.ArrayList;

import com.cubrid.cubridmanager.core.common.model.ServerInfo;

/**
 * This class is responsible to manage all CUBRID Manager server
 * 
 * @author pangqiren
 * @version 1.0 - 2009-6-4 created by pangqiren
 */
public final class ServerManager {

	private static ServerManager manager = new ServerManager();
	ArrayList<ServerInfo> serverInfos = new ArrayList<ServerInfo>();

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

	public void addServer(String hostAddress, int port, String userName, ServerInfo serverInfo){
		ServerInfo info = null;
		if((info = getServer(hostAddress, port, userName)) != null){
			serverInfos.remove(info);
			serverInfos.add(serverInfo);
		}else{
			serverInfos.add(serverInfo);
		}
	}
	
	public void removeServer(String hostAddress, int port, String userName){
		ServerInfo info = null;
		if((info = getServer(hostAddress, port, userName)) != null){
			serverInfos.remove(info);
		}
	}
	
	public ServerInfo getServer(String hostAddress, int port, String userName){
		for(ServerInfo info : serverInfos){
			if(info.getHostAddress().compareTo(hostAddress) == 0 &&
				info.getHostMonPort() == port &&
				info.getUserName().compareTo(userName) == 0){
				return info;
			}
		}
		return null;
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
		}
	}	
	
	public ArrayList<ServerInfo> getAllServerInfos(){
		return serverInfos;
	}
}
