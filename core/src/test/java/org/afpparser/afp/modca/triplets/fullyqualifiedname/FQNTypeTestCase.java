package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link FQNType} 
 */
public class FQNTypeTestCase {

    @Test
    public void testFormatByte() {
        testFqnType(0x01, FQNType.replace_first_gid_name);
        testFqnType(0x07, FQNType.font_family_name);
        testFqnType(0x08, FQNType.font_typeface_name);
        testFqnType(0x09, FQNType.modca_resource_hierarchy_ref);
        testFqnType(0x0A, FQNType.begin_resource_group_ref);
        testFqnType(0x0B, FQNType.attribute_gid);
        testFqnType(0x0C, FQNType.process_element_gid);
        testFqnType(0x0D, FQNType.begin_page_group_ref);
        testFqnType(0x11, FQNType.media_type_ref);
        testFqnType(0x41, FQNType.color_management_resource_ref);
        testFqnType(0x6E, FQNType.data_object_font_base_font_id);
        testFqnType(0x7E, FQNType.data_object_font_linked_font_id);
        testFqnType(0x83, FQNType.begin_document_ref);
        testFqnType(0x84, FQNType.begin_resource_object_ref);
        testFqnType(0x85, FQNType.code_page_name_ref);
        testFqnType(0x86, FQNType.font_charset_name_ref);
        testFqnType(0x87, FQNType.begin_page_ref);
        testFqnType(0x8D, FQNType.begin_medium_map_ref);
        testFqnType(0x8E, FQNType.coded_font_name_ref);
        testFqnType(0x98, FQNType.begin_document_index_ref);
        testFqnType(0xB0, FQNType.begin_overlay_ref);
        testFqnType(0xBE, FQNType.data_object_internal_resource_ref);
        testFqnType(0xCA, FQNType.index_element_gid);
        testFqnType(0xCE, FQNType.other_object_data_ref);
        testFqnType(0xDE, FQNType.data_object_external_resource_ref);
    }

    private void testFqnType(int expected, FQNType type) {
        assertEquals((byte) expected, type.getId());
        assertEquals(type, FQNType.getValue((byte) expected));
    }
}
