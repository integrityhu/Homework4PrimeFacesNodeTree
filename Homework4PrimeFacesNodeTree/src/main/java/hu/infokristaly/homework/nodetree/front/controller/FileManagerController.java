package hu.infokristaly.homework.nodetree.front.controller;

import hu.infokristaly.homework.nodetree.front.manager.FileNodeManager;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class FileManagerController extends SessionBasedController {

    @Inject
    private FileNodeManager fileNodeManager;
    
    private boolean initialized = false;

    public FileManagerController() {
    }

    @PostConstruct
    public void init() {
        initialized = true;
    }


    public FileNodeManager getFileNodeManager() {
        return fileNodeManager;
    }

    public void setFileNodeManager(FileNodeManager fileNodeManager) {
        this.fileNodeManager = fileNodeManager;
    }

    public void initFileManager() {
        boolean isAjax = isAjax();
        if (initialized && !isAjax) {
            fileNodeManager.initRootPath();
        }
    }            
}
