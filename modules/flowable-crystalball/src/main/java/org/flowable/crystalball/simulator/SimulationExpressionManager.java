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

package org.flowable.crystalball.simulator;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.Map;

import org.flowable.engine.common.impl.javax.el.CompositeELResolver;
import org.flowable.engine.common.impl.javax.el.ELContext;
import org.flowable.engine.common.impl.javax.el.ELResolver;
import org.flowable.engine.common.impl.javax.el.PropertyNotWritableException;
import org.flowable.engine.impl.el.DefaultExpressionManager;
import org.flowable.engine.impl.interceptor.DelegateInterceptor;
import org.flowable.variable.service.delegate.VariableScope;

/**
 * {@link org.flowable.engine.impl.el.DefaultExpressionManager} that exposes the simulation event in expressions
 *
 * @author martin.grofcik
 */
public class SimulationExpressionManager extends DefaultExpressionManager {

    public SimulationExpressionManager(DelegateInterceptor delegateInterceptor, Map<Object, Object> beans) {
        super(delegateInterceptor, beans, true);
    }

    @Override
    protected ELResolver createElResolver(VariableScope variableScope) {
        CompositeELResolver compositeElResolver = new CompositeELResolver();
        compositeElResolver.add(new SimulationScopeElResolver(variableScope));
        compositeElResolver.add(super.createElResolver(variableScope));
        return compositeElResolver;
    }

    private class SimulationScopeElResolver extends ELResolver {

        public static final String EVENT_CALENDAR_KEY = "eventCalendar";

        protected VariableScope variableScope;

        public SimulationScopeElResolver(VariableScope variableScope) {
            this.variableScope = variableScope;
        }

        @Override
        public Object getValue(ELContext context, Object base, Object property) {
            if (base == null) {
                if (EVENT_CALENDAR_KEY.equals(property)) {
                    context.setPropertyResolved(true);
                    return SimulationRunContext.getEventCalendar();
                }
            }
            return null;
        }

        @Override
        public boolean isReadOnly(ELContext context, Object base, Object property) {
            return true;
        }

        @Override
        public void setValue(ELContext context, Object base, Object property, Object value) {
            throw new PropertyNotWritableException("Variable '" + property + "' is not writable");
        }

        @Override
        public Class<?> getCommonPropertyType(ELContext arg0, Object arg1) {
            return Object.class;
        }

        @Override
        public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext arg0, Object arg1) {
            return null;
        }

        @Override
        public Class<?> getType(ELContext arg0, Object arg1, Object arg2) {
            return Object.class;
        }
    }
}
