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

import org.flowable.cmmn.engine.impl.deployer.CmmnDeploymentManager;
import org.flowable.cmmn.engine.impl.persistence.entity.deploy.CaseDefinitionCacheEntry;
import org.flowable.cmmn.engine.impl.util.CommandContextUtil;
import org.flowable.cmmn.engine.repository.CaseDefinition;
import org.flowable.cmmn.model.Case;
import org.flowable.cmmn.model.CmmnModel;

/**
 * @author Joram Barrez
 */
public class CaseDefinitionUtil {
    
    public static CaseDefinition getCaseDefinition(String caseDefinitionId) {
        CmmnDeploymentManager deploymentManager = CommandContextUtil.getCmmnEngineConfiguration().getDeploymentManager();
        CaseDefinitionCacheEntry cacheEntry = deploymentManager.getCaseDefinitionCache().get(caseDefinitionId);
        if (cacheEntry != null) {
            return cacheEntry.getCaseDefinition();
        }
        return deploymentManager.findDeployedCaseDefinitionById(caseDefinitionId);
    }
    
    public static CmmnModel getCmmnModel(String caseDefinitionId) {
        CmmnDeploymentManager deploymentManager = CommandContextUtil.getCmmnEngineConfiguration().getDeploymentManager();
        CaseDefinitionCacheEntry cacheEntry = deploymentManager.getCaseDefinitionCache().get(caseDefinitionId);
        if (cacheEntry != null) {
            return cacheEntry.getCmmnModel();
        }
        deploymentManager.findDeployedCaseDefinitionById(caseDefinitionId);
        return deploymentManager.getCaseDefinitionCache().get(caseDefinitionId).getCmmnModel();
    }
    
    public static Case getCase(String caseDefinitionId) {
        return getCmmnModel(caseDefinitionId).getPrimaryCase();
    }

}
