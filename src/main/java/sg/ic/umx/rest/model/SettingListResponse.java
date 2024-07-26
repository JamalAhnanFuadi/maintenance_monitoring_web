/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.ic.umx.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.ws.rs.core.Response.Status;
import sg.ic.umx.model.Setting;

/**
 *
 * @author permadi
 */
public class SettingListResponse extends ServiceResponse {


    @JsonProperty("settings")
    private List<Setting> settingList;

    public SettingListResponse() {
        super(Status.OK);
    }

    public List<Setting> getSettingList() {
        return settingList;
    }

    public void setSettingList(List<Setting> settingList) {
        this.settingList = settingList;
    }


}
