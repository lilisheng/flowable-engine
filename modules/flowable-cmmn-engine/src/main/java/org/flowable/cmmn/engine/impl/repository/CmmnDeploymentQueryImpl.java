/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.flowable.cmmn.engine.impl.repository;

import java.io.Serializable;
import java.util.List;

import org.flowable.cmmn.engine.impl.util.CommandContextUtil;
import org.flowable.cmmn.engine.repository.CmmnDeployment;
import org.flowable.cmmn.engine.repository.CmmnDeploymentQuery;
import org.flowable.engine.common.api.FlowableIllegalArgumentException;
import org.flowable.engine.common.impl.AbstractQuery;
import org.flowable.engine.common.impl.interceptor.CommandContext;
import org.flowable.engine.common.impl.interceptor.CommandExecutor;

/**
 * @author Tijs Rademakers
 * @author Joram Barrez
 */
public class CmmnDeploymentQueryImpl extends AbstractQuery<CmmnDeploymentQuery, CmmnDeployment> implements CmmnDeploymentQuery, Serializable {

    private static final long serialVersionUID = 1L;
    protected String deploymentId;
    protected String name;
    protected String nameLike;
    protected String category;
    protected String categoryNotEquals;
    protected String tenantId;
    protected String tenantIdLike;
    protected boolean withoutTenantId;
    protected String parentDeploymentId;
    protected String parentDeploymentIdLike;

    public CmmnDeploymentQueryImpl() {
    }

    public CmmnDeploymentQueryImpl(CommandContext commandContext) {
        super(commandContext);
    }

    public CmmnDeploymentQueryImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    public CmmnDeploymentQueryImpl deploymentId(String deploymentId) {
        if (deploymentId == null) {
            throw new FlowableIllegalArgumentException("Deployment id is null");
        }
        this.deploymentId = deploymentId;
        return this;
    }

    public CmmnDeploymentQueryImpl deploymentName(String deploymentName) {
        if (deploymentName == null) {
            throw new FlowableIllegalArgumentException("deploymentName is null");
        }
        this.name = deploymentName;
        return this;
    }

    public CmmnDeploymentQueryImpl deploymentNameLike(String nameLike) {
        if (nameLike == null) {
            throw new FlowableIllegalArgumentException("deploymentNameLike is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public CmmnDeploymentQueryImpl deploymentCategory(String deploymentCategory) {
        if (deploymentCategory == null) {
            throw new FlowableIllegalArgumentException("deploymentCategory is null");
        }
        this.category = deploymentCategory;
        return this;
    }

    public CmmnDeploymentQueryImpl deploymentCategoryNotEquals(String deploymentCategoryNotEquals) {
        if (deploymentCategoryNotEquals == null) {
            throw new FlowableIllegalArgumentException("deploymentCategoryExclude is null");
        }
        this.categoryNotEquals = deploymentCategoryNotEquals;
        return this;
    }

    public CmmnDeploymentQueryImpl deploymentTenantId(String tenantId) {
        if (tenantId == null) {
            throw new FlowableIllegalArgumentException("deploymentTenantId is null");
        }
        this.tenantId = tenantId;
        return this;
    }

    public CmmnDeploymentQueryImpl deploymentTenantIdLike(String tenantIdLike) {
        if (tenantIdLike == null) {
            throw new FlowableIllegalArgumentException("deploymentTenantIdLike is null");
        }
        this.tenantIdLike = tenantIdLike;
        return this;
    }

    public CmmnDeploymentQueryImpl deploymentWithoutTenantId() {
        this.withoutTenantId = true;
        return this;
    }

    public CmmnDeploymentQueryImpl parentDeploymentId(String parentDeploymentId) {
        if (parentDeploymentId == null) {
            throw new FlowableIllegalArgumentException("parentDeploymentId is null");
        }
        this.parentDeploymentId = parentDeploymentId;
        return this;
    }

    public CmmnDeploymentQueryImpl parentDeploymentIdLike(String parentDeploymentIdLike) {
        if (parentDeploymentIdLike == null) {
            throw new FlowableIllegalArgumentException("parentDeploymentIdLike is null");
        }
        this.parentDeploymentIdLike = parentDeploymentIdLike;
        return this;
    }

    // sorting ////////////////////////////////////////////////////////

    public CmmnDeploymentQuery orderByDeploymentId() {
        return orderBy(CmmnDeploymentQueryProperty.DEPLOYMENT_ID);
    }

    public CmmnDeploymentQuery orderByDeploymenTime() {
        return orderBy(CmmnDeploymentQueryProperty.DEPLOY_TIME);
    }

    public CmmnDeploymentQuery orderByDeploymentName() {
        return orderBy(CmmnDeploymentQueryProperty.DEPLOYMENT_NAME);
    }

    public CmmnDeploymentQuery orderByTenantId() {
        return orderBy(CmmnDeploymentQueryProperty.DEPLOYMENT_TENANT_ID);
    }

    // results ////////////////////////////////////////////////////////

    @Override
    public long executeCount(CommandContext commandContext) {
        checkQueryOk();
        return CommandContextUtil.getCmmnDeploymentEntityManager(commandContext).findDeploymentCountByQueryCriteria(this);
    }

    @Override
    public List<CmmnDeployment> executeList(CommandContext commandContext) {
        checkQueryOk();
        return CommandContextUtil.getCmmnDeploymentEntityManager(commandContext).findDeploymentsByQueryCriteria(this);
    }

    // getters ////////////////////////////////////////////////////////

    public String getDeploymentId() {
        return deploymentId;
    }

    public String getName() {
        return name;
    }

    public String getNameLike() {
        return nameLike;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryNotEquals() {
        return categoryNotEquals;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getTenantIdLike() {
        return tenantIdLike;
    }

    public boolean isWithoutTenantId() {
        return withoutTenantId;
    }

}
