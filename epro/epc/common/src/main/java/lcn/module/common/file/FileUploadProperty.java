package lcn.module.common.file;

import java.io.File;
import java.util.Collections;
import java.util.Set;

public class FileUploadProperty {
	
	private Long maximumSize;
	private Long minimumSize;

	


	private String uploadPath;
	private Set<String> uploadAllowedTypes;
	private Set<String> uploadAllowedExtensions;

	public FileUploadProperty() {
		
		this.maximumSize = Long.valueOf(5242880L);
		this.minimumSize = Long.valueOf(5242881L);
		this.uploadPath = ".";

		this.uploadAllowedTypes = Collections.emptySet();

		this.uploadAllowedExtensions = Collections.emptySet();
	}

	public Long getMaximumSize() {
		return this.maximumSize;
	}

	public void setMaximumSize(Long maximumSize) {
		this.maximumSize = maximumSize;
	}

	public Long getMinimumSize() {
		return this.minimumSize;
	}

	public void setMinimumSize(Long minimumSize) {
		this.minimumSize = minimumSize;
	}
	
	public String getUploadPath() {
		if ((this.uploadPath != null) && (!("".equals(this.uploadPath)))) {
			File uploadPathFile = new File(this.uploadPath);
			return uploadPathFile.getPath() + File.separator;
		}
		File uploadPathFile = new File(".");
		return uploadPathFile.getPath() + File.separator;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath.trim();
	}

	public Set<String> getUploadAllowedTypes() {
		return this.uploadAllowedTypes;
	}

	public void setUploadAllowedTypes(Set<String> uploadAllowedTypes) {
		this.uploadAllowedTypes = uploadAllowedTypes;
	}

	public Set<String> getUploadAllowedExtensions() {
		return this.uploadAllowedExtensions;
	}

	public void setUploadAllowedExtensions(Set<String> uploadAllowedExtensions) {
		this.uploadAllowedExtensions = uploadAllowedExtensions;
	}
}