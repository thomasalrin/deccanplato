/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/
package org.megam.deccanplato.provider.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ram
 * @param <T>
 * 
 */
public class DefaultDataMap<T> implements DataMap<T> {

	private Map<String, T> defaultMap;

	public DefaultDataMap() {
		defaultMap = new HashMap<String, T>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.megam.deccanplato.provider.core.DataMap#map()
	 */
	@Override
	public Map<String, T> map() {
		return defaultMap;
	}
	
	public String name() {
		return "default-datamap";
	}

}
