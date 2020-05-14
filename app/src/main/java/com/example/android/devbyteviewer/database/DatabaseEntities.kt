/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.devbyteviewer.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.devbyteviewer.domain.Video

// TODO_DONE (01) Create the DatabaseEntities class, adding annotations for the class and the primary key.

@Entity
data class DatabaseVideo constructor(

        @PrimaryKey
        val url: String,
        val updated: String,
        val title: String,
        val description: String,
        val thumbnail: String
)

// TODO_DONE (02) Define extension function List<DatabaseVideo>.asDomainModel(), that returns
// a list of <Video>.

 fun List<DatabaseVideo>.asDomainModel(): List<Video>{
     return map {
         Video(
                 url = it.url,
                 title = it.title,
                 description = it.description,
                 updated = it.updated,
                 thumbnail = it.thumbnail
         )
     }
 }
