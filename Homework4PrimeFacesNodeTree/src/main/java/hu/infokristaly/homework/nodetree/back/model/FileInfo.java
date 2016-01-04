/*
 * Copyright 2013 Integrity Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Zoltan Papp
 * 
 */
package hu.infokristaly.homework.nodetree.back.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * File info entity. This stores encrypted file info in database.
 * 
 * @author pzoli
 * 
 */
@Entity
public class FileInfo implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8421049632218672180L;

    /** The FileInfo unique identifier. */
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    
    /** The file name used on file system. */
    @Basic
    @NotNull
    @NotEmpty
    private String fileName;
	
    /** The file location path. */
    @Basic
    @NotNull
    @NotEmpty
    private String path;

    /** The file size. */
    @Basic
    private Long size;
	
    /** MD5SUM. */
    @Basic
    @Column(length=64)
    private String md5sum;
	
    /** The start date of upload method. */
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    /** The end date of upload method. */
    @Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;
		
	/** The user unique identifier. */
	@Basic
	@NotNull
	private Long userId;
		
	/** The original upload file name. */
	@Basic
	private String uploadFileName;
	
	/** The remote IP address where the file come from. */
	@Basic
	private String remoteIP;
	
	/** cookie that assigned for session. */
	@Basic
	private String serialNumber;
    
	/**
     * Instantiates a new file info.
     */
	public FileInfo() {
		super();
	}   

	/**
     * Gets the FileInfo unique identifier.
     * 
     * @return the FileInfo unique identifier
     */
	public Long getId() {
		return this.id;
	}

	/**
     * Sets the FileInfo unique identifier.
     * 
     * @param id
     *            the new FileInfo unique identifier
     */
	public void setId(Long id) {
		this.id = id;
	}   
	
	/**
     * Gets the file name used on file system.
     * 
     * @return the file name used on file system
     */
	public String getFileName() {
		return this.fileName;
	}

	/**
     * Sets the file name used on file system.
     * 
     * @param fileName
     *            the new file name used on file system
     */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}   
	
	/**
     * Gets the file location path.
     * 
     * @return the file location path
     */
	public String getPath() {
		return this.path;
	}

	/**
     * Sets the file location path.
     * 
     * @param path
     *            the new file location path
     */
	public void setPath(String path) {
		this.path = path;
	}   
	
	/**
     * Gets the file size.
     * 
     * @return the file size
     */
	public Long getSize() {
		return this.size;
	}

	/**
     * Sets the file size.
     * 
     * @param size
     *            the new file size
     */
	public void setSize(Long size) {
		this.size = size;
	}   
	
	/**
     * Gets the sSHA password.
     * 
     * @return the sSHA password
     */
	public String getMd5sum() {
		return this.md5sum;
	}

	/**
     * Sets the sSHA password.
     * 
     * @param password
     *            the new sSHA password
     */
	public void setMd5sum(String md5sum) {
		this.md5sum = md5sum;
	}	

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Gets the end date of upload method.
     * 
     * @return the end date of upload method
     */
    public Date getUploadDate() {
		return this.uploadDate;
	}

	/**
     * Sets the end date of upload method.
     * 
     * @param uploadDate
     *            the new end date of upload method
     */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}   
	
    /**
     * Gets the user unique identifier.
     * 
     * @return the user unique identifier
     */
    public Long getUserId() {
		return this.userId;
	}

	/**
     * Sets the user unique identifier.
     * 
     * @param userId
     *            the new user unique identifier
     */
	public void setUserId(Long userId) {
		this.userId = userId;
	}   
		
	/**
     * Gets the original upload file name.
     * 
     * @return the original upload file name
     */
	public String getUploadFileName() {
		return this.uploadFileName;
	}

	/**
     * Sets the original upload file name.
     * 
     * @param uploadFileName
     *            the new original upload file name
     */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

    /**
     * Gets the remote IP address where the file come from.
     * 
     * @return the remote IP address where the file come from
     */
    public String getRemoteIP() {
        return remoteIP;
    }

    /**
     * Sets the remote IP address where the file come from.
     * 
     * @param remoteIP
     *            the new remote IP address where the file come from
     */
    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    /**
     * Gets the cookie that assigned for session.
     * 
     * @return the cookie that assigned for session
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the cookie that assigned for session.
     * 
     * @param cookie
     *            the new cookie that assigned for session
     */
    public void setSerialNumber(String cookie) {
        this.serialNumber = cookie;
    }

    /**
     * Gets the size for human reader.
     * 
     * @return the size for human reader
     */
    public String getSizeForHumanReader() {
        if (getSize() == null) {
            return null;
        }
        
        BigDecimal resultSize = new BigDecimal(getSize());
        String sizeUnitForHumanReader = "byte";
        if (resultSize.longValue() > (1024)) {
            resultSize = resultSize.divide(BigDecimal.valueOf(1024));
            sizeUnitForHumanReader = "KByte";
            if (resultSize.longValue() > 1024) {
                resultSize = resultSize.divide(BigDecimal.valueOf(1024));
                sizeUnitForHumanReader = "MByte";
                if (resultSize.longValue() > 1024) {
                    resultSize = resultSize.divide(BigDecimal.valueOf(1024));
                    sizeUnitForHumanReader = "GiB";
                }
            }
        }
        return String.format("%.2f %s", resultSize, sizeUnitForHumanReader);
    }
    
    /**
     * Gets the short upload file name.
     * 
     * @return the short upload file name
     */
    public String getShortUploadFileName() {
        return (uploadFileName.length() > 25 ? uploadFileName.substring(0, 25)+"..." : uploadFileName);
    }
}
