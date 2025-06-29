package model;

import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
public class ProjectDTO {

    private int projId;
    private String projName;
    private String description;
    private String status;
    private LocalDate est;

    public ProjectDTO() {
    }

    public ProjectDTO(int projId, String projName, String description, String status, LocalDate est) {
        this.projId = projId;
        this.projName = projName;
        this.description = description;
        this.status = status;
        this.est = est;
    }

    public int getProjId() {
        return projId;
    }

    public void setProjId(int projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getEst() {
        return est;
    }

    public void setEst(LocalDate est) {
        this.est = est;
    }

}
