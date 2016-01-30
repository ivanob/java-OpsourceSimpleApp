package controller.operations;

import controller.persistence.ServerDAO;

/**
 * Created by ivan on 19/1/16.
 * It implements the Command method. Each operation is separated in a different
 * class so the method execute() is implemented in each of them.
 */
public interface Operation {
    public void execute();
}

