package com.portal.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ServiceNotFoundException
 *
 * @author Mrhan
 * @date 2021/7/4 0:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ServiceNotFoundException extends RuntimeException{

    private String serviceName;
}
