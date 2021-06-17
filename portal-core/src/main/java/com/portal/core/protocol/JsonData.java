package com.portal.core.protocol;

import com.portal.core.server.AbstractData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * JsonData
 *
 * @author Mrhan
 * @date 2021/6/17 9:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JsonData extends AbstractData {

    private Protocol<JsonData>  protocol;

}
