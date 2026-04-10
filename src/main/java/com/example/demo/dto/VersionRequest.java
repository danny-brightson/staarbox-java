package com.example.demo.dto;

public class VersionRequest {

    private String currentVersion;
    private String latestVersion;
    private String updateType; // "FORCE" or "SOFT"
	public String getCurrentVersion() {
		return currentVersion;
	}
	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	public String getLatestVersion() {
		return latestVersion;
	}
	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}
	public String getUpdateType() {
		return updateType;
	}
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	public VersionRequest(String currentVersion, String latestVersion, String updateType) {
		super();
		this.currentVersion = currentVersion;
		this.latestVersion = latestVersion;
		this.updateType = updateType;
	}
	public VersionRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Getters & Setters
    
}
