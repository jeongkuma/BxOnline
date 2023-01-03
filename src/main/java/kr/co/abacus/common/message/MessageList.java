package kr.co.abacus.common.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MessageList implements Serializable {
	/**
	 * @Desctiption :
	 */
	private static final long serialVersionUID = 1L;

	private String msg;

}
