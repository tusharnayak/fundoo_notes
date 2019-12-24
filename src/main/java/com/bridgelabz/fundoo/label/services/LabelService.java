package com.bridgelabz.fundoo.label.services;

import com.bridgelabz.fundoo.label.dto.LabelDto;
import com.bridgelabz.fundoo.response.Response;

public interface LabelService {
	public Response createLabel(LabelDto labeldto,String token);
	
	public Response deleteLabel(String token,String labelid);
	
	public Response updateLabel(LabelDto labeldto,String token,String labelid);
	
	public Response labelNoteAdd(String noteid, String labelid, String token);

}
