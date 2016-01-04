package hu.infokristaly.homework.nodetree.front.manager;

import hu.infokristaly.homework.nodetree.back.model.FileInfo;
import hu.infokristaly.homework.nodetree.back.settings.ApplicationSettings;
import hu.infokristaly.homework.nodetree.utils.StringToolkit;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import java.io.Serializable;

@Named
@SessionScoped
public class FileNodeManager implements Serializable {

    private static final long serialVersionUID = -7515245185945573561L;

    @Inject
    private ApplicationSettings applicationSettings;
    
    @Inject
    private Logger log;
    
    @Inject
    private FacesContext facesContext;

    private File[] directories;
    private List<FileInfo> filesInDir;
    private String rootPath;
    private TreeNode rootNode;
    private List<FileInfo> filteredFileList;

    private TreeNode rootDir;
    private TreeNode selectedNode;

    private void updateFilesInDir(String path) {
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                boolean result = ! pathname.isDirectory();
                return result;
            }
        };
        File[] files = listDir(path, fileFilter);
        filesInDir = convertFileArrayToEntity(files);        
    }
    
    private List<FileInfo> convertFileArrayToEntity(File[] files) {
        ArrayList<FileInfo> result = new ArrayList<FileInfo>();
        for(File file : files) {
            FileInfo fInfo = new FileInfo();
            fInfo.setFileName(file.getName());
            fInfo.setPath(getNodePath(selectedNode));
            fInfo.setLastModified(new Date(file.lastModified()));
            fInfo.setSize(file.length());
            result.add(fInfo);
        }
        return result;
    }

    public void reloadFilesInDir() {
        updateFilesInDir(applicationSettings.getUploadRoot() + getNodePath(selectedNode));
    }
    
    private static File[] listDir(String root, FileFilter filter) {
        File[] result = null;
        File dir = new File(root);
        if (filter != null) {
            result = dir.listFiles(filter);
        } else {
            result = dir.listFiles();
        }
        return result;
    }

    public void initRootPath() {
        String uploadRoot = applicationSettings.getUploadRoot();
        String userRoot = StringToolkit.getCFileName("");
        setRootPath(uploadRoot + "/" + userRoot);
        File userRootPath = new File(getRootPath());
        if (!userRootPath.exists()) {
            if (userRootPath.mkdirs()) {
                log.severe("directory created: " + userRootPath.getAbsolutePath());
            } else {
                log.severe("directory creation exception: " + userRootPath.getAbsolutePath());
            }
        } else {
            log.severe("directory exists: " + userRootPath.getAbsolutePath());
        }
        rootNode = new DefaultTreeNode("Root", null);
        rootDir = new DefaultTreeNode("/", rootNode);
        loadNodeSubtree("/", rootDir);
        for (TreeNode node : rootDir.getChildren()) {
            loadNodeSubtree("/" + node.toString(), node);
        }
        rootDir.setSelected(true);
    }

    public void loadNodeSubtree(String nodePath, TreeNode node) {
        FileFilter dirFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                boolean result = pathname.isDirectory() && !pathname.isHidden();
                return result;
            }
        };
        File[] directories = listDir(getRootPath() + nodePath, dirFilter);
        if (directories != null) {
            for (File dir : directories) {
                Iterator<TreeNode> iter = node.getChildren().iterator();
                boolean found = false;
                while (iter.hasNext() && !found) {
                    found = iter.next().toString().equals(dir.getName());
                }
                if (!found) {
                    new DefaultTreeNode(dir.getName(), node);
                }
            }
        }
    }

    public String getNodePathToRoot(TreeNode node) {
        String result = "";
        if (!node.equals(rootDir)) {
            do {
                result = "/" + node.toString() + result;
                node = node.getParent();
            } while (!node.equals(rootDir));
        } else {
            result = "/";
        }
        return result;
    }

    private String getNodePath(TreeNode treeNode) {
        return treeNode.equals(rootDir) ? "/" : getNodePathToRoot(treeNode) + "/";
    }

    public void onNodeExpand(NodeExpandEvent event) {
        TreeNode node = event.getTreeNode();
        String nodePath = getNodePathToRoot(node);
        loadNodeSubtree(nodePath, node);
        List<TreeNode> dirs = node.getChildren();
        if (dirs != null) {
            for (TreeNode subNode : dirs) {
                loadNodeSubtree(nodePath + "/" + subNode.toString(), subNode);
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", nodePath);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        String nodePath = getNodePath(event.getTreeNode());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", nodePath);
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        updateFilesInDir(applicationSettings.getUploadRoot() + nodePath);
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public File[] getFiles() {
        return directories;
    }

    public void setFiles(File[] files) {
        this.directories = files;
    }
    
    public List<FileInfo> getFilesInDir() {
        return filesInDir;
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }
                     
    public List<FileInfo> getFilteredFileList() {
        return filteredFileList;
    }

    public void setFilteredFileList(List<FileInfo> filteredFileList) {
        this.filteredFileList = filteredFileList;
    }
}
