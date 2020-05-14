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

package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching devbyte - videos from the network and storing them on disk (database)
 */

class VideosRepository(private val database: VideosDatabase){

        val videos: LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()){         //convert your LiveData list of DatabaseVideo objects to domain Video objects
        it.asDomainModel()
    }

    suspend fun refreshVideos(){                                                         // 'suspend' means Coroutines-friendly
        withContext(Dispatchers.IO){                                                     // for read / write operations (to-disk) that are very slow
            val playlist = Network.devbytes.getPlaylist().await()     // wait until data is fetched, but without blocking the MainThread
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}

